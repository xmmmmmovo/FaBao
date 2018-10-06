package com.example.lab.android.nuc.law_analysis.video;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.news.ImageLoader;
import com.example.lab.android.nuc.law_analysis.news.adapter.AbsListAdapter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/*
视频的Adapter
 */
public class VideoAdapter extends AbsListAdapter<VideoBean> {
    public VideoAdapter() {
        super(R.layout.item_video);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, VideoBean item) {
        baseViewHolder.setText( R.id.tv_video_source, item.getTopicName());
        baseViewHolder.setText(R.id.tv_video_playcount, item.getPlayCount() + "次播放");
        ImageLoader.getInstance().withRound(mContext, item.getTopicImg(), (ImageView) baseViewHolder.getView(R.id.img_video_logo));
        JCVideoPlayerStandard standard = baseViewHolder.getView(R.id.videoplayer);
        standard.setUp(item.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, item.getTitle());
        ImageLoader.getInstance().with(mContext, item.getCover(), standard.thumbImageView);
    }
}
