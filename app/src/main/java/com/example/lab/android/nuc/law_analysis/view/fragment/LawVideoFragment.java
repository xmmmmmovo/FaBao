package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.adapter.JiaoziAdapter;
import com.example.lab.android.nuc.law_analysis.base.Jiaozi;
import com.example.lab.android.nuc.law_analysis.R;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

public class LawVideoFragment extends Fragment implements View.OnClickListener{


    private RecyclerView mRecyclerView;
    private JiaoziAdapter mJiaoziAdapter;
    private ArrayList<Jiaozi> mJiaozis;
    private ArrayList<String> titles;
    private ArrayList<String> mUris;

    public static LawVideoFragment newInstance(ArrayList<String> titles, ArrayList<String> uris){
        Bundle bundle = new Bundle( );
        LawVideoFragment lawVideoFragment = new LawVideoFragment();
        bundle.putStringArrayList( "title",titles );
        bundle.putStringArrayList( "uri",uris );
        lawVideoFragment.setArguments( bundle );
        return lawVideoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_law_video1,container,false);
        mRecyclerView = view.findViewById( R.id.law_video_recycler  );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        Bundle args = getArguments();
        if (args != null) {
            titles = args.getStringArrayList( "title" );
            mUris = args.getStringArrayList( "uri" );
        }
        initJiaozi();
        mJiaoziAdapter = new JiaoziAdapter( mJiaozis,getContext() );
        mRecyclerView.setAdapter( mJiaoziAdapter );
        return view;
    }

    private void initJiaozi(){
        mJiaozis = new ArrayList<>(  );
        for (int i = 0; i < titles.size() - 1; i++) {
            mJiaozis.add( new Jiaozi( titles.get( i ),mUris.get( i ) ) );
        }
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Jzvd.releaseAllVideos();
        mJiaozis = null;
        titles = null;
        mUris = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }



    @Override
    public void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }
}