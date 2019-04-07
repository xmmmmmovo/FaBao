package com.example.lab.android.nuc.law_analysis.video;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.lab.android.nuc.law_analysis.application.Config;
import com.example.lab.android.nuc.law_analysis.news.adapter.AbsListAdapter;
import com.example.lab.android.nuc.law_analysis.news.util.SnackBarUtil;
import com.example.lab.android.nuc.law_analysis.view.fragment.AbsListFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.Call;
import okhttp3.Response;
/*
法制视频的fragment
 */
public class VideoFragment extends AbsListFragment<VideoBean> {

    public static VideoFragment newInstance(String type) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mID", type);
        videoFragment.setArguments(bundle);
        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mID = args.getString("mID");
        }
    }

    @Override
    protected AbsListAdapter<VideoBean> getAdapter() {
        return new VideoAdapter();
    }

    @Override
    protected void onClick(VideoBean videoBean, int position) {
    }

    @Override
    protected void getData(final String id, int index) {
        OkGo.get( Config.getVideoUrl(id, index))
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            List<VideoBean> beans = new ArrayList<>();
                            Gson gson = new Gson();
                            JsonParser parser = new JsonParser();
                            JsonObject jsonObj = parser.parse(s).getAsJsonObject();
                            JsonElement jsonElement = jsonObj.get(id);
                            if (null == jsonElement) {
                                onDataSuccessReceived(null, LOADNOMORE);
                            } else {
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                if (jsonArray.size() == 0) {
                                    onDataSuccessReceived(null, LOADNOMORE);
                                } else {
                                    for (int i = 1; i < jsonArray.size(); i++) {
                                        JsonObject jo = jsonArray.get(i).getAsJsonObject();
                                        VideoBean bean = gson.fromJson(jo, VideoBean.class);
                                        beans.add(bean);
                                    }
                                    onDataSuccessReceived(beans, LOADSUCCESS);
                                }
                            }
                        } catch (Exception e) {
                            SnackBarUtil.showSnackBar(e.getMessage(), mSwipeRefreshLayout, getActivity());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        SnackBarUtil.showSnackBar(e.getMessage(), mSwipeRefreshLayout, getActivity());
                        onDataSuccessReceived(null, LOADFAIL);
                    }
                });
    }

    @Override
    protected View.OnAttachStateChangeListener getChangeListener() {
        return new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                JCVideoPlayer.releaseAllVideos();
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelAll();
    }
}