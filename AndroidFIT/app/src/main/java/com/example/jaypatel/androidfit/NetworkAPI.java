package com.example.jaypatel.androidfit;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;


public class NetworkAPI {

    //Fields
    DataWrapper dataWrapper;
    RequestQueue networkQueue;
    ArrayDeque<StringRequest> apiQueue;
    boolean apiBusy = false;
    public String email = "rcarrion4io@gmail.com";
    public Gson gson;


    //Constructor
    public NetworkAPI(Context context, DataWrapper dataWrapper) {
        networkQueue = Volley.newRequestQueue(context);
        apiQueue = new ArrayDeque<>();
        this.dataWrapper = dataWrapper;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }


    private class CloudRecover{
        public ArrayList<DataWrapper.Workout> workouts;
        public ArrayList<DataWrapper.Exercise> exercises;
    }

    public void recoverFromCloud(){
        StringRequest request = getNetworkRequest(CallBack.RECOVER_FROM_CLOUD,Request.Method.GET, RESTUrls.RECOVER + email);
        addNetworkRequestToQueue(request);
    }
    public void handleRecoverFromCloud(String data){
        CloudRecover recoveredData = gson.fromJson(data,CloudRecover.class);
        dataWrapper.setExercises(recoveredData.exercises);
        dataWrapper.setWorkouts(recoveredData.workouts);
    }



    /****EXERCISE methods*************************************************************************/



    // GET EXERCISES
    public void getExercises() {
        StringRequest request = getNetworkRequest(CallBack.GET_EXERCISE_CALLBACK,Request.Method.GET, RESTUrls.GET_EXERCISES);
        addNetworkRequestToQueue(request);
    }
    public void handleGetExercisesResponse(boolean isError, String data){

    }


    // ADD EXERCISES
    public void addExercise(DataWrapper.Exercise exercise) {
        exercise.email = email;
        StringRequest request = getNetworkRequest(CallBack.ADD_EXERCISE_CALLBACK,Request.Method.POST, RESTUrls.ADD_EXERCISE,exercise);
        addNetworkRequestToQueue(request);
    }
    public void handleAddExerciseResponse(boolean isError, String exerciseJSON){
        //update mongo id in exercise list
        if(exerciseJSON.equals("ERROR")){
            //do somenthing
        }
        DataWrapper.Exercise e = gson.fromJson(exerciseJSON, DataWrapper.Exercise.class);
        dataWrapper.setExerciseMongoID(e);
    }


    // EDIT EXERCISES
    public void editExercise(DataWrapper.Exercise exercise) {
        StringRequest request = getNetworkRequest(CallBack.EDIT_EXERCISE_CALLBACK,Request.Method.PUT, RESTUrls.EDIT_EXERCISE,exercise);
        addNetworkRequestToQueue(request);
    }
    public void handleEditExerciseResponse(boolean isError, String data){

    }


    // DELETE EXERCISES
    public void deleteExercise(DataWrapper.Exercise exercise) {
        StringRequest request = getNetworkRequest(CallBack.DELETE_EXERCISE_CALLBACK,Request.Method.DELETE, RESTUrls.DELETE_EXERCISE + exercise._id);
        addNetworkRequestToQueue(request);
    }
    public void handleDeleteExerciseResponse(boolean isError, String data){

    }











    /****WORKOUTS methods**************************************************************************/

    // GET WORKOUTS
    public void getWorkouts() {
        StringRequest request = getNetworkRequest(CallBack.GET_WORKOUTS_CALLBACK,Request.Method.GET, RESTUrls.GET_WORKOUTS);
        addNetworkRequestToQueue(request);
    }
    public void handleGetWorkoutsResponse(boolean isError, String data){

    }



    // ADD WORKOUTS
    public void addWorkout(DataWrapper.Workout workout) {
        workout.email = email;
        StringRequest request = getNetworkRequest(CallBack.ADD_WORKOUT_CALLBACK,Request.Method.POST, RESTUrls.ADD_WORKOUT,workout);
        addNetworkRequestToQueue(request);
    }
    public void handleAddWorkoutResponse(boolean isError, String data){
        if(data == "ERROR"){

        } else {
            DataWrapper.Workout workout = gson.fromJson(data,DataWrapper.Workout.class);
            dataWrapper.setWorkoutMongoID(workout);
        }

    }


    // DELETE WORKOUTS
    public void deleteWorkout(DataWrapper.Workout workout) {
        StringRequest request = getNetworkRequest(CallBack.DELETE_WORKOUT_CALLBACK,Request.Method.DELETE, RESTUrls.DELETE_WORKOUT + workout._id);
        addNetworkRequestToQueue(request);
    }
    public void handleDeleteWorkoutResponse(boolean isError, String data){

    }











    /****Network Request Utility Methods***********************************************************/
    private StringRequest getNetworkRequest(final CallBack responseHandler, int method, String url) {
        StringRequest request = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Update DataWrapper using its methods
                        handleCallBack(responseHandler,response,false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //retry max of 5 times then ignore
                        handleCallBack(responseHandler,error.toString(),true);
                    }
                }
        );
        return request;
    }

    private StringRequest getNetworkRequest(final CallBack responseHandler, int method, String url, Object data) {
        final String jsonData = gson.toJson(data);

        StringRequest request = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //function of the method that called
                        //handleCallBack(responseHandler,response,false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleCallBack(responseHandler,error.toString(),true);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonData.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of INSERT payload here using %s", "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    String responseData = null;
                    try {
                        responseData = new String(response.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    handleCallBack(responseHandler,responseData,false);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        return request;
    }

    private void addNetworkRequestToQueue(StringRequest networkRequest) {
        if(!apiBusy){
            apiBusy = true;
            networkQueue.add(networkRequest);
            return;
        } else {
            apiQueue.add(networkRequest);
        }
        /*
        TODO
        1.) Call method to send network request
            (Dont allow other request to be added to queue until first request done)
                -add to intermediate queue

        1.5) Check request went through, didnt time out, if it did try again.
        2.) Update DataWrapper using its methods
            --Add (do nothing, mongo can be used as back up now)
                (Update the mongoid for the record)
            --Edit (do nothing, mongo can be used as back up)
                (How do we know which record to update, id must be unique in local and mongo table)
                (After adding it network will return records unique id in mongo, store this id locally)
            --DELETE (do nothing, already locally removed, simply remove from mongo)

            --GET WORKOUTS|EXERCISES
                -update the local information, simply replace with mongo db

         */
    }

    private void handleCallBack(CallBack responseHandler, String response, boolean isError){
        switch(responseHandler){

            case RECOVER_FROM_CLOUD:
                handleRecoverFromCloud(response);

            //Exercises
            case GET_EXERCISE_CALLBACK:
                handleGetExercisesResponse(isError,response);
                break;
            case ADD_EXERCISE_CALLBACK:
                handleAddExerciseResponse(isError,response);
                break;
            case EDIT_EXERCISE_CALLBACK:
                handleEditExerciseResponse(isError,response);
                break;
            case DELETE_EXERCISE_CALLBACK:
                handleDeleteExerciseResponse(isError,response);
                break;



            //Workout
            case GET_WORKOUTS_CALLBACK:
                handleGetWorkoutsResponse(isError,response);
                break;
            case ADD_WORKOUT_CALLBACK:
                handleAddWorkoutResponse(isError,response);
                break;
            case DELETE_WORKOUT_CALLBACK:
                handleDeleteWorkoutResponse(isError,response);
                break;
        }
        processNetworkQueue();//will send next request if queue is not empty
    }

    private void processNetworkQueue(){
        //gets called after successfull network request has finished safe to do new request
        apiBusy = false;
        if(apiQueue.isEmpty())
            return;

        StringRequest request = apiQueue.pop();
        addNetworkRequestToQueue(request);
    }




    /***Network Helper Data ***********************************************************************/
    //URL endpoints for rest api
    private static class RESTUrls {
        public static String BASE = "https://ricardo-api.herokuapp.com/androidfit/";//
        public static String RECOVER = BASE + "recover/";
        //Exercise
        public static String GET_EXERCISES = BASE + "exercises";
        public static String ADD_EXERCISE = BASE + "exercise";
        public static String EDIT_EXERCISE = BASE + "exercise";//add id
        public static String DELETE_EXERCISE = BASE + "exercise/";//add id

        //Workout
        public static String GET_WORKOUTS = BASE + "workouts";
        public static String ADD_WORKOUT = BASE + "workout";
        public static String DELETE_WORKOUT = BASE + "workout/";//add id
    }

    //Used to identify which method to call back from network request
    private enum CallBack{
        RECOVER_FROM_CLOUD,

        GET_EXERCISE_CALLBACK,
        ADD_EXERCISE_CALLBACK,
        EDIT_EXERCISE_CALLBACK,
        DELETE_EXERCISE_CALLBACK,

        GET_WORKOUTS_CALLBACK,
        ADD_WORKOUT_CALLBACK,
        DELETE_WORKOUT_CALLBACK
    }
}
