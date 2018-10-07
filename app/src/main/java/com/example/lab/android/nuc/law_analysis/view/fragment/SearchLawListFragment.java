package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.MainAdapter;
import com.example.lab.android.nuc.law_analysis.base.Law;
import com.example.lab.android.nuc.law_analysis.utils.tools.GlideImageLoader;
import com.example.lab.android.nuc.law_analysis.view.activity.Search_Intent_Activity;
import com.tangguna.searchbox.library.widget.SearchLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

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
                    "http://www.npc.gov.cn/npc/lfzt/rlys/2014-10/24/content_1882701.htm" ),
            new Law( "土地保护法","http://pfx0y0vgp.bkt.clouddn.com/law2.jpg",
                    "http://www.gov.cn/banshi/2005-05/26/content_989.htm" ),
            new Law( "宪法","http://pfx0y0vgp.bkt.clouddn.com/law3.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%AE%AA%E6%B3%95" ),
            new Law( "国籍法","http://pfx0y0vgp.bkt.clouddn.com/law4.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E8%8F%AF%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9C%8B%E5%9C%8B%E7%B1%8D%E6%B3%95" ),
            new Law( "兵役法","http://pfx0y0vgp.bkt.clouddn.com/law5.jpg",
            "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%85%B5%E5%BD%B9%E6%B3%95" ),
            new Law( "药品管理法","http://pfx0y0vgp.bkt.clouddn.com/law6.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E8%8D%AF%E5%93%81%E7%AE%A1%E7%90%86%E6%B3%95" ),
            new Law( "野生动物保护法","http://pfx0y0vgp.bkt.clouddn.com/law7.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E9%87%8E%E7%94%9F%E5%8A%A8%E7%89%A9%E4%BF%9D%E6%8A%A4%E6%B3%95" ),
            new Law( "城乡规划法","http://pfx0y0vgp.bkt.clouddn.com/law8.jpg",
                    "hhttps://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%9F%8E%E4%B9%A1%E8%A7%84%E5%88%92%E6%B3%95" ),
            new Law( "旅游法","http://pfx0y0vgp.bkt.clouddn.com/law9.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E6%97%85%E6%B8%B8%E6%B3%95" ),
            new Law( "网络安全法","http://pfx0y0vgp.bkt.clouddn.com/law10.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E7%BD%91%E7%BB%9C%E5%AE%89%E5%85%A8%E6%B3%95" ),
            new Law( "专利法","http://pfx0y0vgp.bkt.clouddn.com/law11.jpg",
            "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E4%B8%93%E5%88%A9%E6%B3%95" ),
            new Law( "著作权法","http://pfx0y0vgp.bkt.clouddn.com/law12.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E8%91%97%E4%BD%9C%E6%9D%83%E6%B3%95" ),
            new Law( "物权法","http://pfx0y0vgp.bkt.clouddn.com/law13.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E8%8F%AF%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9C%8B%E7%89%A9%E6%AC%8A%E6%B3%95" ),
            new Law( "未成年人保护法","http://pfx0y0vgp.bkt.clouddn.com/law14.jpg",
                    "https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E6%9C%AA%E6%88%90%E5%B9%B4%E4%BA%BA%E4%BF%9D%E6%8A%A4%E6%B3%95" )

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
