package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.util.tools.GlideImageLoader;
import com.example.lab.android.nuc.new_idea.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class LawNewsFragment extends Fragment implements View.OnClickListener {
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public LawNewsFragment() {
        // Required empty public constructor
    }

    public static LawNewsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LawNewsFragment fragment = new LawNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.law_news_fragment, container, false);
        Banner banner = (Banner) view.findViewById( R.id.banner );
        banner.setBannerStyle( BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation( Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity( BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        return view;
    }

    private void init(){
        images.add( "http://pd35yssng.bkt.clouddn.com/i1.jpg" );
        images.add( "http://pd35yssng.bkt.clouddn.com/i2.jpg" );
        images.add( "http://pd35yssng.bkt.clouddn.com/i3.jpg" );
        images.add( "http://pd35yssng.bkt.clouddn.com/i4.jpg" );
        images.add( "http://pd35yssng.bkt.clouddn.com/i5.jpg" );
        titles.add( "夫妻因为一张小照片告上法庭，其中的法律纠葛你知道吗" );
        titles.add( "《法律讲堂（生活版）》 20180530 爱恨纠葛被毁容| CCTV社会与法" );
        titles.add( "夫妻关系无法挽回，财产分割问题意见不一，双方决定用法律" );
        titles.add( "以案说法：城镇居民购买农民土地建房被判决无效！" );
        titles.add( "农民工意外受伤引纠纷 人民调解来帮忙" );
    }

    @Override
    public void onClick(View view) {

    }
}
