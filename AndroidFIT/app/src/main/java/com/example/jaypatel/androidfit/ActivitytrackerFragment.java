package com.example.jaypatel.androidfit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivitytrackerFragment extends Fragment {

    public ActivitytrackerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activitytracker_fragment,container,false);
        //  return super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }
}
