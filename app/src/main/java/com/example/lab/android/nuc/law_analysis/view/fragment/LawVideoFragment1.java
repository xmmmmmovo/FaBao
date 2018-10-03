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
import com.example.lab.android.nuc.law_analysis.R;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

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
        mJiaozis.add( new Jiaozi( "2018婚姻法，缺了这9个，谁也离不了婚？ ",
                "http://pd35yssng.bkt.clouddn.com/woc.mp4" ) );
        mJiaozis.add( new Jiaozi( "2018新婚姻法规定：3种情况离婚净身出户，懂了吗？",
                "http://pd35yssng.bkt.clouddn.com/woc6.mp4" ) );
        mJiaozis.add( new Jiaozi( "2018年，新婚姻法，起诉离婚后，查询法官的正确打开方式？",
                "http://pd35yssng.bkt.clouddn.com/woc7.mp4" ) );
        mJiaozis.add( new Jiaozi( "辽视第一时间 - 婚姻法",
                "http://pd35yssng.bkt.clouddn.com/woc5.mp4" ) );
        mJiaozis.add( new Jiaozi( "今年新婚姻法，不管结婚几十年，这项规定女方一分钱拿不到 ",
                "http://pd35yssng.bkt.clouddn.com/woc1.mp4" ) );
        mJiaozis.add( new Jiaozi( "中国访谈 -- 世界对话之新婚姻法的推出",
                "http://pd35yssng.bkt.clouddn.com/wocc1.mp4" ) );
        mJiaozis.add( new Jiaozi( "2018婚姻法新规定，房产证即使有你名字，离婚时也可能分不到钱",
                "http://pd35yssng.bkt.clouddn.com/wocc2.mp4" ) );
        mJiaozis.add( new Jiaozi( "《婚姻法》有规定！这几种情况房子写了你名字也未必是你的！" ,
                "http://pd35yssng.bkt.clouddn.com/wocc3.mp4") );
        mJiaozis.add( new Jiaozi("妻子频遭家暴怀着孕也被殴打，丈夫：我就这个样，你可以离婚啊！",
                "http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1810%252F01%252FUWXnL338L%252FHD%252FUWXnL338L-mobile.mp4"));
        mJiaozis.add( new Jiaozi( "2018新婚姻法，关于未婚同居都做了哪些规定？不知道可吃大亏了！",
                "http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1808%252F21%252FRhFCn026a%252FHD%252FRhFCn026a-mobile.mp4") );
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