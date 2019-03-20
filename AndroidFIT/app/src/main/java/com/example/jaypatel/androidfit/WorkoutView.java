package com.example.jaypatel.androidfit;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WorkoutView extends AppCompatActivity {

    private ArrayList<DataWrapper.Workout> workoutlist;

    String exerciseID, exerciseName;
    DataWrapper wrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);

//        Calendar  calendar = Calendar.getInstance();
//        String currentdate = DateFormat.getDateInstance().format(calendar.getTime());
//        TextView textViewdate = findViewById(R.id.date);
//        textViewdate.setText(currentdate);

        wrapper = DataWrapper.getSingleton(getBaseContext());
        wrapper.recoverFromCloud();

        workoutlist = wrapper.getWorkouts();



        for(int i = 0 ; i < workoutlist.size(); i++)
        {
            Log.e("workout" ,workoutlist.get(i).uuid);
            for(int j = 0; j < workoutlist.get(i).sets.size(); j++)
            {
                Log.e("set#", String.valueOf(i));
                Log.e("rep", String.valueOf(workoutlist.get(i).sets.get(j).reps));
                Log.e("lbs", String.valueOf(workoutlist.get(i).sets.get(j).weight));
            }
        }


        TextView name = findViewById(R.id.exe_id);
        final EditText reps = findViewById(R.id.reps);
        final EditText pounds = findViewById(R.id.pounds);
        //final TextView date = findViewById(R.id.date);
        Button btnSave = findViewById(R.id.btn_SaveWorkout);
        Button btnSet = findViewById(R.id.addsets);


        if(getIntent().hasExtra("exerciseID") && getIntent().hasExtra("exerciseName")){
            exerciseID = getIntent().getStringExtra("exerciseID");
            exerciseName = getIntent().getStringExtra("exerciseName");
            name.setText(exerciseName);
        }

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // int d = Integer.parseInt(date.getText().toString());
                int r = Integer.parseInt(reps.getEditableText().toString());
                int p = Integer.parseInt(pounds.getEditableText().toString());
                DataWrapper.WorkoutSet workoutSet = new DataWrapper.WorkoutSet(p,r);
                DataWrapper.Workout workout = new DataWrapper.Workout(exerciseID);
                workout.sets.add(workoutSet);

                wrapper.addWorkout(workout);

                Toast.makeText(getApplicationContext(),"Set added",Toast.LENGTH_SHORT).show();
            }
        });


    }





    }

