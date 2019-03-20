package com.example.jaypatel.androidfit;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class ExerciseFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private ArrayList<DataWrapper.Exercise> exelist;
    public DataWrapper data;
    public Dialog addwrk;
    public Button btnsave,btnx;

    public ExerciseFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.exercise_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.exe_item);

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter((getContext()),data.getExercises(), this.data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
//TO EDIT OR DELETE



//FLOATING BUTTON CODE
        FloatingActionButton addbtn = (FloatingActionButton) view.findViewById(R.id.newworkout);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addwrk = new Dialog(getActivity());
                addwrk.setContentView(R.layout.addexedialog);
                btnsave = (Button)addwrk.findViewById(R.id.btn_save);
                btnx = (Button)addwrk.findViewById(R.id.btn_x);

           btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text  = ((EditText)addwrk.findViewById(R.id.exe_id)).getText().toString();
                        DataWrapper.Exercise e = new DataWrapper.Exercise(text);
                        data.addExercise(e);
                        addwrk.dismiss();


                    }
                });

                btnx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        addwrk.dismiss();

                    }
                });


                addwrk.show();
            }
        });

        return view;

    }
////FLOATING END

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = DataWrapper.getSingleton(getContext());
        data.recoverFromCloud();
    }


}

