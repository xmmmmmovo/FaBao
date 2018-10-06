package com.example.lab.android.nuc.law_analysis.news.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
/*
标题栏TabLayout的Adapter
 */
public class TitleAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    private String[] titles;

    public TitleAdapter(FragmentManager fm,List<Fragment> fragments,String[] titles){
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public Fragment getItem(int position){
        return fragments.get( position );
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 8)
            return null;
        else
            return titles[position];
    }
}
