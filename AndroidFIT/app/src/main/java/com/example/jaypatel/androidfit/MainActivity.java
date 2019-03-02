package com.example.jaypatel.androidfit;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

private ViewPager viewPager;
private ViewPageAdapter adapter;
private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    tabLayout=(TabLayout) findViewById(R.id.tablayout_id);
    viewPager=(ViewPager)findViewById(R.id.viewpager_id);
       adapter =new ViewPageAdapter(getSupportFragmentManager());
        //add frags here
        adapter.addFragment(new ExerciseFragment(),"Exercise");
        adapter.addFragment(new HistoryFragment(),"History");
        adapter.addFragment(new ActivitytrackerFragment(),"Activity");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager );

    }

}
