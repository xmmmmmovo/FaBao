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
import android.widget.LinearLayout;

import com.example.lab.android.nuc.law_analysis.adapter.MagicPagerAdapter;
import com.example.lab.android.nuc.new_idea.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

public class LawVideoFragment extends Fragment implements View.OnClickListener{

    private static final String[] CHANNELS = new String[]{"婚姻纠葛", "土地纠纷", "专利产权","养老医疗","盗窃抢劫"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ViewPager mViewPager;

    public static LawVideoFragment newInstance(){
        Bundle bundle = new Bundle( );
        LawVideoFragment lawVideoFragment = new LawVideoFragment();
        lawVideoFragment.setArguments( bundle );
        return lawVideoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_law_video,container,false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        MagicPagerAdapter mMagicPagerAdapter = new MagicPagerAdapter( getChildFragmentManager() );
        mMagicPagerAdapter.addFragment( LawVideoFragment1.newInstance() );
        mMagicPagerAdapter.addFragment( LawVideoFragment1.newInstance() );
        mMagicPagerAdapter.addFragment( LawVideoFragment1.newInstance() );
        mMagicPagerAdapter.addFragment( LawVideoFragment1.newInstance() );
        mMagicPagerAdapter.addFragment( LawVideoFragment1.newInstance() );
        mViewPager.setAdapter( mMagicPagerAdapter );
        initMagicIndicator1(view);
        return view;
    }

    private void initMagicIndicator1(View view) {
        MagicIndicator magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator1);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor( Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding( UIUtil.dip2px(getContext(), 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}