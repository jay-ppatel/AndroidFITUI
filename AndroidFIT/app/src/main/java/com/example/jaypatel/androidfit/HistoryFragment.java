package com.example.jaypatel.androidfit;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoryFragment extends Fragment {

    private ArrayList<DataWrapper.Workout> workoutlist;
    public Dialog addwrk;
    public Button btnsave, btnx;
    public DataWrapper data;

    View view;
    private RecyclerView recyclerView;

    public HistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.history_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.workout_list_id);

        final HistoryViewAdapter recyclerViewAdapter = new HistoryViewAdapter((getContext()),data.getWorkouts(),data.getExercises(),this.data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);


        workoutlist = data.getWorkouts();

        if(workoutlist != null)
        {

            Log.e("workoutlist is not null", String.valueOf(workoutlist.size()));
        }






        FloatingActionButton addbtn = (FloatingActionButton) view.findViewById(R.id.newworkout);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addwrk = new Dialog(getActivity());
                addwrk.setContentView(R.layout.addexedialog);
                btnsave = (Button) addwrk.findViewById(R.id.btn_save);
                btnx = (Button) addwrk.findViewById(R.id.btn_x);

                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = ((EditText) addwrk.findViewById(R.id.exe_id)).getText().toString();


                        DataWrapper.Exercise e = new DataWrapper.Exercise(text);
                        data.addExercise(e);
                        addwrk.dismiss();


                    }
                });

                btnx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String text  = ((EditText)addwrk.findViewById(R.id.exe_id)).getText().toString();
//                        //TextView nText = (TextView) addwrk.findViewById(R.id.exe_id);
//
//                        DataWrapper.Exercise e = new DataWrapper.Exercise(text);
//                        data.addExercise(e);
                        addwrk.dismiss();

                    }
                });


                addwrk.show();
            }
        });

        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = DataWrapper.getSingleton(getContext());
        data.recoverFromCloud();
    }
}

