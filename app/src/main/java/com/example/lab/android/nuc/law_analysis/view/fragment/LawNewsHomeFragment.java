package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.news.adapter.TitleAdapter;
import com.example.lab.android.nuc.law_analysis.application.Config;
import com.example.lab.android.nuc.law_analysis.news.ScrollEvent;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class LawNewsHomeFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;
    private List<Fragment> mFragments = new ArrayList<>(  );

    private String[] mTitles = {"法律头条","法制财经","科技执法","法制周刊","法制娱乐","体育","汽车","足球"};

    private String[] mIDs = {Config.HEADLINE_ID, Config.CAR_ID, Config.FOOTBALL_ID, Config.ENTERTAINMENT_ID,
            Config.SPORTS_ID, Config.FINANCE_ID, Config.TECH_ID, Config.MOVIE_ID};

    private TitleAdapter mAdapter;

    public static LawNewsHomeFragment newInstance(){
        LawNewsHomeFragment lawNewsHomeFragment = new LawNewsHomeFragment();
        Bundle bundle = new Bundle();
        lawNewsHomeFragment.setArguments( bundle );
        return lawNewsHomeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mFragments.add(NewsFragment.newInstance( Config.HEADLINE_ID ));
        mFragments.add(NewsFragment.newInstance(Config.FINANCE_ID));
        mFragments.add(NewsFragment.newInstance(Config.TECH_ID));
        mFragments.add(NewsFragment.newInstance(Config.MOVIE_ID));
        mFragments.add( NewsFragment.newInstance( Config.ENTERTAINMENT_ID ) );
        mFragments.add(NewsFragment.newInstance(Config.SPORTS_ID));
        mFragments.add( NewsFragment.newInstance( Config.CAR_ID ) );
        mFragments.add( NewsFragment.newInstance( Config.FOOTBALL_ID ) );

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_news_home,container,false);
        mViewPager = (ViewPager) view.findViewById( R.id.news_home_viewpager );
        mTabLayout = (TabLayout) view.findViewById( R.id.tablayout );
        init();
        mFloatingActionButton = (FloatingActionButton) view.findViewById( R.id.fab_news );
        mFloatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mViewPager.getCurrentItem();
                EventBus.getDefault().post(new ScrollEvent(mIDs[position]));
            }
        } );
        return view;
    }

    protected void init() {
        mAdapter = new TitleAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
