package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.news.adapter.AbsListAdapter;
import com.example.lab.android.nuc.law_analysis.news.Animation.CustomAnimation;
import com.example.lab.android.nuc.law_analysis.news.ScrollEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public abstract class AbsListFragment<T> extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;

    protected String mID = "";

    protected AbsListAdapter<T> mAdapter;

    protected int mIndex = 0;

    protected int mDelayMillis = 500;

    protected abstract AbsListAdapter<T> getAdapter();

    protected abstract void onClick(T t, int position);

    protected abstract void getData(String id, int index);

    public static final int LOADFAIL = 0000;//加载失败

    public static final int LOADSUCCESS = 0001;//加载成功

    public static final int LOADNOMORE = 0002;//没有更多数据



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.frg_base,container,false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipefreshlayout );
        mRecyclerView = (RecyclerView) view.findViewById( R.id.recyclerview_news );
        init();
        return view;
    }

    private void init() {

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.black));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = getAdapter();
        // 添加动画
        mAdapter.openLoadAnimation(new CustomAnimation());
        mAdapter.isFirstOnly(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                T t = (T) adapter.getData().get(position);
                onClick(t, position);
            }
        });
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnAttachStateChangeListener(getChangeListener());
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (null != mSwipeRefreshLayout) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        mIndex = 0;
        getData(mID, mIndex);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mIndex = 0;
        getData(mID, mIndex);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        getData(mID, mIndex);
    }

    @NonNull
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected View.OnAttachStateChangeListener getChangeListener() {
        return new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        };
    }

    /**
     * @param tList
     * @param loadCode
     */
    protected final void onDataSuccessReceived(List<T> tList, int loadCode) {
        switch (loadCode) {
            case LOADFAIL:
                if (null != mSwipeRefreshLayout) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                mAdapter.loadMoreFail();
                mIndex = 0;
                break;
            case LOADSUCCESS:
                if (mIndex == 0) {
                    mAdapter.setNewData(tList);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (null != mSwipeRefreshLayout) {
//                                mSwipeRefreshLayout.setRefreshing(false);
//                                mAdapter.setEnableLoadMore(true);
//                            }
//                        }
//                    }, mDelayMillis);
                    if (null != mSwipeRefreshLayout) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    mAdapter.setEnableLoadMore(true);
                } else {
                    mAdapter.addData(tList);
                    mAdapter.loadMoreComplete();
                    mSwipeRefreshLayout.setEnabled(true);
                }
                mIndex = mIndex + 20;
                break;
            case LOADNOMORE:
                if (null != mSwipeRefreshLayout) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                mAdapter.loadMoreEnd();
                mIndex = 0;
                break;
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrollEvent(ScrollEvent event) {
        handleScrollEvent(event.getId());
    }

    /**
     * 返回顶部
     * @param id
     */
    @Subscribe
    protected void handleScrollEvent(String id) {

    }


}
