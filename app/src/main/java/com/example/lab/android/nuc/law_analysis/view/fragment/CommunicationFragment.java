package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.lab.android.nuc.law_analysis.utils.views.ColorFlipPagerTitleView;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.MagicPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;
/*
容纳律师联系人跟法律讲坛的fragment
 */
public class CommunicationFragment extends Fragment implements View.OnClickListener{

    private View root;
    private List<String> mTitleDataList = new ArrayList<>(  );
    private ViewPager mViewPager;

    public static CommunicationFragment newInstance(){
        Bundle bundle = new Bundle( );
        CommunicationFragment communicationFragment = new CommunicationFragment();
        communicationFragment.setArguments( bundle );
        return communicationFragment;
    }

    public CommunicationFragment(){
        mTitleDataList.add( "律师列表");
        mTitleDataList.add( "法律社区");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate( R.layout.fragment4,container,false);
        initViewPager();
        initmagicIndicator();
        return root;
    }

    private void initViewPager(){
        mViewPager = root.findViewById( R.id.community_view_pager);
        MagicPagerAdapter adapter = new MagicPagerAdapter( getChildFragmentManager() );
        adapter.addFragment(LawyersFragment.newInstance());
        adapter.addFragment( LawyersChatFragment.newInstance() );
        mViewPager.setAdapter( adapter );
    }

    private void initmagicIndicator(){
        MagicIndicator magicIndicator = root.findViewById( R.id.magic_indicator_community );
        magicIndicator.setBackgroundColor( Color.parseColor( "#fafafa" ) );
        CommonNavigator commonNavigator = new CommonNavigator( getContext() );
        commonNavigator.setScrollPivotX( 0.65f );
        commonNavigator.setAdapter( new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView( context);
                simplePagerTitleView.setText( mTitleDataList.get( index ) );
                simplePagerTitleView.setNormalColor( Color.parseColor( "#9e9e9e" ) );
                simplePagerTitleView.setSelectedColor( Color.parseColor( "#FC704F" ) );
                simplePagerTitleView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem( index );
                    }
                } );
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator( context );
                indicator.setMode( LinePagerIndicator.MODE_EXACTLY );
                indicator.setLineHeight( UIUtil.dip2px( context,6 ) );
                indicator.setLineWidth( UIUtil.dip2px( context,10 ) );
                indicator.setRoundRadius( UIUtil.dip2px( context,3 ) );
                indicator.setStartInterpolator( new AccelerateInterpolator(  ) );
                indicator.setEndInterpolator( new DecelerateInterpolator( 2.0f ) );
                indicator.setColors( Color.parseColor( "#FC704F" ) );
                return indicator;
            }
        } );
        magicIndicator.setNavigator( commonNavigator );
        ViewPagerHelper.bind( magicIndicator,mViewPager );
    }

    @Override
    public void onClick(View v) {

    }
}