package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.adapter.JiaoziAdapter;
import com.example.lab.android.nuc.law_analysis.base.Jiaozi;
import com.example.lab.android.nuc.law_analysis.util.tools.GlideImageLoader;
import com.example.lab.android.nuc.new_idea.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class LawVideoFragment1 extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private JiaoziAdapter mJiaoziAdapter;
    private ArrayList<Jiaozi> mJiaozis;

    public static LawVideoFragment1 newInstance(){
        Bundle bundle = new Bundle( );
        LawVideoFragment1 lawVideoFragment1 = new LawVideoFragment1();
        lawVideoFragment1.setArguments( bundle );
        return lawVideoFragment1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        initJiaozi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_law_video1,container,false);
        mRecyclerView = view.findViewById( R.id.law_video_recycler  );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        mJiaoziAdapter = new JiaoziAdapter( mJiaozis,getContext() );
        mRecyclerView.setAdapter( mJiaoziAdapter );
        return view;
    }

    private void initJiaozi(){
        mJiaozis = new ArrayList<>(  );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4" ) );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://jzvd.nathen.cn/63f3f73712544394be981d9e4f56b612/69c5767bb9e54156b5b60a1b6edeb3b5-5287d2089db37e62345123a1be272f8b.mp4" ) );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://tv.cntv.cn/video/C10604/b19db92b23f94dd4bf93f77795f57433" ) );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://tv.cntv.cn/video/C10604/b19db92b23f94dd4bf93f77795f57433" ) );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://tv.cntv.cn/video/C10604/b19db92b23f94dd4bf93f77795f57433" ) );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://tv.cntv.cn/video/C10604/b19db92b23f94dd4bf93f77795f57433" ) );
        mJiaozis.add( new Jiaozi( "《法律讲堂（文史版）》 20140714 马丁-路德-金遇刺案（一） 歧视很严重",
                "http://tv.cntv.cn/video/C10604/b19db92b23f94dd4bf93f77795f57433" ) );
    }

    @Override
    public void onClick(View v) {

    }
}