package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.MainAdapter;
import com.example.lab.android.nuc.law_analysis.bean.Law;
import com.example.lab.android.nuc.law_analysis.view.activity.Search_Intent_Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchLawListFragment extends Fragment  {

    private View view;
    private EditText editText;
    int y;
    private List<Law> mLawList = new ArrayList<>(  );

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;

    public static SearchLawListFragment newInstance() {
        Bundle args = new Bundle();
        SearchLawListFragment fragment = new SearchLawListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
               WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view = inflater.inflate(R.layout.search_law_list_fragment, container, false);
        editText = view.findViewById(R.id.et_searchtext_search);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Search_Intent_Activity.class);
                startActivity(i);
            }
        });
        initLaws();
        mRecyclerView = (RecyclerView) view.findViewById( R.id.recycler_view );
        GridLayoutManager layoutManager = new GridLayoutManager( getContext(),2 );
        mRecyclerView.setLayoutManager(layoutManager);
        mMainAdapter = new MainAdapter( mLawList);
        mRecyclerView.setAdapter( mMainAdapter );
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh );
        mSwipeRefreshLayout.setColorSchemeResources( R.color.green_normal );
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLaws();
            }
        } );
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
            animation.setDuration(520);
            animation.setFillAfter(true);
            getView().startAnimation(animation);
        }
    }

    private Law[] mLaws = {
            new Law( "中华人民共和国婚姻法","http://pfx0y0vgp.bkt.clouddn.com/law1.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=e172f20ef00ca3143348ec093db4d647_law"),
            new Law( "土地保护法","http://pfx0y0vgp.bkt.clouddn.com/law2.jpg",
                    "https://m.baidu.com/sf_bk/item/%E5%9C%9F%E5%9C%B0%E7%AE%A1%E7%90%86%E6%B3%95/10599533?fr=aladdin&ms=1&rid=7266152629620528448"),
            new Law( "宪法","http://pfx0y0vgp.bkt.clouddn.com/law3.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=febc490f0c5df76f774ce8620f489505_law" ),
            new Law( "国籍法","http://pfx0y0vgp.bkt.clouddn.com/law4.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=daa798023ad6753beebaa16635945b40_law"),
            new Law( "兵役法","http://pfx0y0vgp.bkt.clouddn.com/law5.jpg",
            "https://qianxun.baidu.com/claw/detail?id=6349ef500e72fb656f07826e99778484_law"),
            new Law( "药品管理法","http://pd35yssng.bkt.clouddn.com/laww.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=97d2d786501c1c6bd4a1245f7355b630_law"),
            new Law( "野生动物保护法","http://pfx0y0vgp.bkt.clouddn.com/law7.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=96946dc4d2bb3eb9475b0754972cda9b_law"),
            new Law( "城乡规划法","http://pfx0y0vgp.bkt.clouddn.com/law8.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=b0326ded5796bc9bf12e61dca8bcc011_law"),
            new Law( "旅游法","http://pd35yssng.bkt.clouddn.com/laww.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=b0326ded5796bc9bf12e61dca8bcc011_law"),
            new Law( "网络安全法","http://pfx0y0vgp.bkt.clouddn.com/law10.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=f66f830e45c0490d589f1de2fe05e942_law"),
            new Law( "专利法","http://pfx0y0vgp.bkt.clouddn.com/law11.jpg",
            "https://qianxun.baidu.com/claw/detail?id=f2fbdfe42b917584b619091db6d1d877_law" ),
            new Law( "著作权法","http://pfx0y0vgp.bkt.clouddn.com/law12.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=f2fbdfe42b917584b619091db6d1d877_law"),
            new Law( "物权法","http://pfx0y0vgp.bkt.clouddn.com/law13.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=b6332f9dce8eda63c47d2f7c9ccf0234_law"),
            new Law( "未成年人保护法","http://pfx0y0vgp.bkt.clouddn.com/law14.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=0639c1c221f3c543f519b37e6c36c2f4_law"),
            new Law( "继承法","http://pd35yssng.bkt.clouddn.com/laww1.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=7c44e917fbc2901880de00bbaeb4ec75_law"),
            new Law( "收养法","http://pd35yssng.bkt.clouddn.com/laww9.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=63dfa52a8a56e29ca771adaab4cb3bed_law"),
            new Law( "保险法","http://pd35yssng.bkt.clouddn.com/laww3.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=995d3f3bcf96060c74df1af2f6fce4d8_law"),
            new Law( "残疾人保障法","http://pd35yssng.bkt.clouddn.com/laww4.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=d5692e17f57aa0b3efcb1ed2439abd7e_law"),
            new Law( "老年人权益保障法","http://pd35yssng.bkt.clouddn.com/laww5.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=85ef4ccdb814922c894cdddd7b35e5ff_law"),
            new Law( "劳动合同法","http://pd35yssng.bkt.clouddn.com/laww6.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=d5fff5d3dfa03ad24257a9fb02e77b80_law"),
            new Law( "民办教育促进法","http://pd35yssng.bkt.clouddn.com/laww7.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=4bc42ca1d2f8013ffff602473e93e465_law"),
            new Law( "气象法","http://pd35yssng.bkt.clouddn.com/laww8.jpg",
            "https://qianxun.baidu.com/claw/detail?id=3dafbdbbe0c0f22cc5f81601216682a8_law"),
            new Law( "英雄烈士保护法","http://pd35yssng.bkt.clouddn.com/laww8.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=3dafbdbbe0c0f22cc5f81601216682a8_law"),
            new Law( "刑法","http://pfx0y0vgp.bkt.clouddn.com/law01.jpg",
            "https://qianxun.baidu.com/claw/detail?id=44c1d146805db411b3d1ffa09c8ef0c3_law"),
            new Law( "文物保护法","hhttp://pfx0y0vgp.bkt.clouddn.com/law02.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=4136c7f315035c7fc6952a0c37f7c569_law"),
            new Law( "义务教育法","http://pfx0y0vgp.bkt.clouddn.com/law03.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=2584bd5d60687f0ff1ea22c082dc154f_law"),
            new Law( "教师法","http://pfx0y0vgp.bkt.clouddn.com/law04.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=cc0ac166f9cf78bf65fe2aea3c6d9456_law"),
            new Law( "体育法","http://pfx0y0vgp.bkt.clouddn.com/law05.jpg",
                    "https://qianxun.baidu.com/claw/detail?id=54f61bb0ff96a7e633db538e5e9a6bb8_law"),
            new Law( "枪支管理法","http://pfx0y0vgp.bkt.clouddn.com/law06.jpg",
                    "hhttps://qianxun.baidu.com/claw/detail?id=2f5d7bc3b0513246fb758a70d010b466_law"),
            new Law( "献血法","http://pfx0y0vgp.bkt.clouddn.com/law07.jpg",
                    "https://m.baidu.com/sf_bk/item/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E7%8C%AE%E8%A1%80%E6%B3%95?ms=1&rid=8136428579347858824"),

    };

    private void initLaws(){
        mLawList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random(  );
            int index = random.nextInt( mLaws.length );
            mLawList.add( mLaws[index] );
        }
    }


    private void refreshLaws(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep( 2000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        initLaws();
                        mMainAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing( false );
                    }
                } );
            }
        } ).start();
    }
    public View getView() {
        return view;
    }

}
