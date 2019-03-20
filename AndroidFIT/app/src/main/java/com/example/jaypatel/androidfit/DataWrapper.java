package com.example.jaypatel.androidfit;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class DataWrapper {

    /* DATA classes */
    static public class WorkoutSet{
        public int weight;
        public int reps;

        public WorkoutSet(int weight, int reps){
            this.reps = reps;
            this.weight = weight;
        }
    }

    static public class Workout {
        public String _id;
        public String uuid;
        public String email;
        public String exerciseUUID;
        public String date;

        public ArrayList<WorkoutSet> sets;

        public Workout(String exerciseUUID){
            uuid = UUID.randomUUID().toString();
            this.exerciseUUID = exerciseUUID;
            sets = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
            this.date = dateFormat.format(new Date());
        }
    }

    static public class Exercise{
        public String _id;
        public String email;
        public String uuid;
        public String name;

        Exercise(String name){
            this.name = name;
        }
    }


    /* SINGLETON methods*/
    private static DataWrapper singleton;
    private Context context;

    private static final String WORKOUTS_FILE_NAME = "com.androidfit.workoutFile";
    private static final String EXERCISE_FILE_NAME = "com.androidfit.exerciseFile";

    private static final String WORKOUT_JSON_NAME = "workoutList";
    private static final String EXERCISE_JSON_NAME = "exerciseList";

    private SharedPreferences workoutPref;
    private SharedPreferences exercisePref;

    private SharedPreferences.Editor workoutEditor;
    private SharedPreferences.Editor exerciseEditor;

    public Gson gson;
    private NetworkAPI api;


    private DataWrapper(Context context){
        this.context = context;
        api = new NetworkAPI(context,this);

        workoutPref = this.context.getSharedPreferences(WORKOUTS_FILE_NAME,this.context.MODE_PRIVATE);
        exercisePref = this.context.getSharedPreferences(EXERCISE_FILE_NAME,this.context.MODE_PRIVATE);

        workoutEditor = workoutPref.edit();
        exerciseEditor = exercisePref.edit();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public static DataWrapper getSingleton(Context context){
        if (singleton == null)
            singleton = new DataWrapper(context);
        return singleton;
    }


    public void recoverFromCloud(){
        api.recoverFromCloud();
    }






    /*EXERCISE methods*/
    public ArrayList<Exercise> getExercises(){
        ArrayList<Exercise> exercise = new ArrayList<Exercise>();

        String exerciseJSON = exercisePref.getString(EXERCISE_JSON_NAME,null);

        if(exerciseJSON == null)
            return exercise;

        Type exerciseListType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        exercise = gson.fromJson(exerciseJSON, exerciseListType);
        return exercise;
    }

    //sets local data to provided array
    public void setExercises(ArrayList<Exercise> exercises){
        String json = gson.toJson(exercises);
        exerciseEditor.putString(EXERCISE_JSON_NAME,json);
        exerciseEditor.apply();
    }

    public void addExercise(Exercise exercise){
        ArrayList<Exercise> exercises = getExercises();
        exercise.uuid = UUID.randomUUID().toString();
        exercises.add(exercise);

        setExercises(exercises);
        api.addExercise(exercise);
    }

    public void setExerciseMongoID(Exercise exercise){
        UUID targetID = UUID.fromString(exercise.uuid);

        ArrayList<Exercise> exercises = getExercises();

        for(int i = 0; i < exercises.size();i++){

            Exercise e = exercises.get(i);
            UUID eID = UUID.fromString(e.uuid);
            if(eID.equals(targetID)){
                //found exercise
                e.email = exercise.email;
                e._id = exercise._id;
                setExercises(exercises);
                return;
            }

        }
    }

    public void editExercise(String exerciseUUID, String newExerciseName){
        UUID targetID = UUID.fromString(exerciseUUID);

        ArrayList<Exercise> exercises = getExercises();

        for(int i = 0; i < exercises.size();i++){
            Exercise e = exercises.get(i);
            UUID eID = UUID.fromString(e.uuid);
            if(eID.equals(targetID)){
                //found exercise
                e.name = newExerciseName;
                setExercises(exercises);
                api.editExercise(e);
                return;
            }
        }
    }

    public void deleteExercise(String exerciseUUID){
        UUID targetID = UUID.fromString(exerciseUUID);

        ArrayList<Exercise> exercises = getExercises();

        for(int i = 0; i < exercises.size();i++){
            Exercise e = exercises.get(i);
            UUID eID = UUID.fromString(e.uuid);
            if(eID.equals(targetID)){
                //found exercise
                exercises.remove(i);
                setExercises(exercises);
                api.deleteExercise(e);
                return;
            }
        }
    }





    /*WORKOUTS methods*/
    public ArrayList<Workout> getWorkouts(){
        ArrayList<Workout> workouts = new ArrayList<>();

        String workoutJSON = workoutPref.getString(WORKOUT_JSON_NAME,null);

        if(workoutJSON == null)
            return workouts;

        Type workoutListType = new TypeToken<ArrayList<Workout>>(){}.getType();
        workouts = gson.fromJson(workoutJSON, workoutListType);
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts){
        String json = gson.toJson(workouts);
        workoutEditor.putString(WORKOUT_JSON_NAME,json);
        workoutEditor.apply();
    }

    public void addWorkout(Workout workout){
        ArrayList<Workout> history = getWorkouts();
        history.add(workout);

        setWorkouts(history);
        api.addWorkout(workout);
    }

    public void setWorkoutMongoID(Workout workout){
        UUID targetID = UUID.fromString(workout.uuid);

        ArrayList<Workout> workouts = getWorkouts();

        for(int i = 0; i < workouts.size();i++){

            Workout w = workouts.get(i);
            UUID wID = UUID.fromString(w.uuid);
            if(wID.equals(targetID)){
                //found exercise
                w.email = workout.email;
                w._id = workout._id;

                setWorkouts(workouts);
                return;
            }

        }
    }
    public void deleteWorkout(String workoutUUID){
        UUID targetID = UUID.fromString(workoutUUID);

        ArrayList<Workout> workouts = getWorkouts();

        for(int i = 0; i < workouts.size();i++){
            Workout w = workouts.get(i);
            UUID wID = UUID.fromString(w.uuid);
            if(wID.equals(targetID)){
                //found exercise
                workouts.remove(i);
                setWorkouts(workouts);
                api.deleteWorkout(w);
                return;
            }
        }
    }



}
