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

public class LawVideoFragment3 extends Fragment implements View.OnClickListener{


    private RecyclerView mRecyclerView;
    private JiaoziAdapter mJiaoziAdapter;
    private static ArrayList<Jiaozi> mJiaozis;

    public static LawVideoFragment3 newInstance(){
        Bundle bundle = new Bundle( );
        LawVideoFragment3 lawVideoFragment3 = new LawVideoFragment3();
        lawVideoFragment3.setArguments( bundle );
        return lawVideoFragment3;
    }


    public static void setJiaozis(ArrayList<Jiaozi> jiaozis) {
        mJiaozis = jiaozis;
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
        mJiaozis.add( new Jiaozi( "最高法：加大知识产权侵权违法惩治力度",
                "http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1802%252F28%252FYHdpn7061%252FHD%252FYHdpn7061-mobile.mp4" ) );
        mJiaozis.add( new Jiaozi( "农村土地纠纷，两家大打出手，为征土地年迈老人被打倒在地 ",
                "http://pd35yssng.bkt.clouddn.com/1245008497_3012156243_20170430180531.mp4" ) );
        mJiaozis.add( new Jiaozi( "划分土地起争执 村干部竟当众露下体侮辱女村民",
                "http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1809%252F26%252FgoBOS340H%252FSD%252FgoBOS340H-mobile.mp4" ) );
        mJiaozis.add( new Jiaozi( "五大措施教你处理土地纠纷",
                "http://pd35yssng.bkt.clouddn.com/we.mp4" ) );
        mJiaozis.add( new Jiaozi( "处理土地纠纷要遵循哪些原则",
                "http://pd35yssng.bkt.clouddn.com/we1.mp4" ) );
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
