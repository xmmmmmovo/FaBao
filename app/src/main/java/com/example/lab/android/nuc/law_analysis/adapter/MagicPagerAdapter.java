package com.example.lab.android.nuc.law_analysis.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
/*
二哥的那个滑动粘连点
 */

public class MagicPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>(  );

    public MagicPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    public void addFragment(Fragment fragment){
        mFragments.add( fragment );
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get( position );
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
