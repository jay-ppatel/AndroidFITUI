package com.example.jaypatel.androidfit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExerciseFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private List<Exercises> exelist;

    public ExerciseFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.exercise_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.exe_item);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter((getContext()),exelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.exe_item);
//        ListAdapter listAdapter = new ListAdapter();
//        recyclerView.setAdapter(listAdapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity())
//;
//        recyclerView.setLayoutManager(layoutManager) ;
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        exelist = new ArrayList<>();
         exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("LEGS  : LEG PRESS"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises("Chest  : Bench Press"));
        exelist.add(new Exercises());





    }
}

