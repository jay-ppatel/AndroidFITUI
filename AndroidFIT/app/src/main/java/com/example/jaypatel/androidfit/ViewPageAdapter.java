package com.example.jaypatel.androidfit;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {


    //variables to track of fragment and the frag titles
    private final List<Fragment>  mFragList = new ArrayList<>();
    private final List<String> mFragTitles = new ArrayList<>();

    public void addFragment(Fragment fragment,String title){
        mFragList.add(fragment);
        mFragTitles.add(title);
    }

    //constructor
    public ViewPageAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragTitles.get(position);

    }

    @Override
    public Fragment getItem(int i) {

        return mFragList.get(i);
    }

    @Override
    public int getCount() {
        return mFragList.size();
    }
}
