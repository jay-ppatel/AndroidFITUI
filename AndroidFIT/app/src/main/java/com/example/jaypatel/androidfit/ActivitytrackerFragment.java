package com.example.jaypatel.androidfit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivitytrackerFragment extends Fragment {


    TextView txtTimer;
    Button btnStart,btnPause,btnLap;
    Handler customHandler = new Handler();
    LinearLayout cons ;
    Long startTimer = 0L, millisec = 0L, swapbuffer= 0L,updateTimer= 0L;


    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
           millisec = SystemClock.uptimeMillis() - startTimer;
           updateTimer = swapbuffer+millisec;
           int secs= (int) (updateTimer/1000);
           int mins = secs/60;
           secs%= 60;
           int milliseconds = (int)(updateTimer%1000);
           txtTimer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d", milliseconds));
           customHandler.postDelayed(this,0);



        }
    };



    public ActivitytrackerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activitytracker_fragment,container,false);
        //  return super.onCreateView(inflater, container, savedInstanceState);



        btnStart =  (Button) view.findViewById(R.id.startButton);
        btnPause =  (Button) view.findViewById(R.id.pauseButton);
        btnLap =    (Button) view.findViewById(R.id.lapButton);
        txtTimer =  (TextView) view.findViewById(R.id.timerValue);
        cons =  (LinearLayout) view.findViewById(R.id.cons);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startTimer = SystemClock.uptimeMillis();

                customHandler.postDelayed(updateTimerThread,0);


            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapbuffer+=millisec;
                customHandler.removeCallbacks(updateTimerThread);


            }
        });
        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              LayoutInflater inflater1 = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              View addview = inflater1.inflate(R.layout.time,null);
              TextView txtValue =(TextView)addview.findViewById(R.id.textContent);
              txtValue.setText(txtTimer.getText());
              cons.addView(addview);



            }
        });


        return view;
    }

}
