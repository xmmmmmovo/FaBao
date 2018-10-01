package com.example.lab.android.nuc.new_idea.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.new_idea.view.fragment.Fragment1;
import com.example.lab.android.nuc.new_idea.view.fragment.Fragment2;
import com.example.lab.android.nuc.new_idea.view.fragment.Fragment3;
import com.example.lab.android.nuc.new_idea.view.fragment.Fragment4;
import com.example.lab.android.nuc.new_idea.view.fragment.Fragment5;

public class PageAdapter extends FragmentPagerAdapter {

    private final int COUNT = 5;

    public Context mContext;

    public PageAdapter(FragmentManager fm,Context context) {
        super( fm );
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Fragment1.newInstance();
            case 1:
                return Fragment2.newInstance();
            case 2:
                return Fragment3.newInstance();
            case 3:
                return Fragment4.newInstance();
            case 4:
                return Fragment5.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

}
