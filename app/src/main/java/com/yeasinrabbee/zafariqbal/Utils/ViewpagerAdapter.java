package com.yeasinrabbee.zafariqbal.Utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragments ;
    public FragmentManager fragmentManager;

    public ViewpagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> fragments) {
        super(fm, behavior);
        this.fragmentManager = fm;
        this.fragments = fragments;

    }



    @NonNull
    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);


    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
