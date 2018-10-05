package com.example.lab.android.nuc.law_analysis.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lab.android.nuc.law_analysis.view.fragment.CommunicationFragment;
import com.example.lab.android.nuc.law_analysis.view.fragment.LawNewsFragment;
import com.example.lab.android.nuc.law_analysis.view.fragment.LawNewsHomeFragment;
import com.example.lab.android.nuc.law_analysis.view.fragment.LawVideoFragment;
import com.example.lab.android.nuc.law_analysis.view.fragment.MainAnalysisFragment;
import com.example.lab.android.nuc.law_analysis.view.fragment.SearchLawListFragment;

import java.util.List;

/*
主界面的viewpager的Adapter
 */
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
                return SearchLawListFragment.newInstance();
            case 1:
                return LawNewsHomeFragment.newInstance();
            case 2:
                return MainAnalysisFragment.newInstance();
            case 3:
                return CommunicationFragment.newInstance();
            case 4:
                return LawVideoFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

}
