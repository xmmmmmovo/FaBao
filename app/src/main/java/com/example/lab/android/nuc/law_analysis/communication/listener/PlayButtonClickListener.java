package com.example.lab.android.nuc.law_analysis.communication.listener;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.lab.android.nuc.law_analysis.application.MyApplication;
import com.example.lab.android.nuc.law_analysis.communication.bean.ChatMessageBean;
import com.example.lab.android.nuc.law_analysis.communication.widget.PlayButton;

import java.io.File;

/**
 * Created by wanghao15536870732 on 2018/6/05/024.
 */

public class PlayButtonClickListener implements View.OnClickListener {
    private PlayButton playButton;
    private boolean leftSide;
    private Context context;
    private String path;

    private AnimationDrawable anim;
    public static PlayButtonClickListener mCurrentPlayButtonClickListner = null;
    private MediaPlayer mediaPlayer = null;
    public static boolean isPlaying = false;
    // 将MediaPlayer播放地址，
    public static String audioPath;
    private ImageView ivIsListened;
    private ChatMessageBean chatMessageBean;

    public PlayButtonClickListener(PlayButton playButton, ImageView ivIsListened, ChatMessageBean chatMessageBean, boolean leftSide, Context context, String path) {
        this.playButton = playButton;
        this.leftSide = leftSide;
        this.context = context;
        this.path = path;
        this.ivIsListened = ivIsListened;
        this.chatMessageBean = chatMessageBean;
    }

    @Override
    public void onClick(View v) {
        if (isPlaying) {
            if (TextUtils.equals(audioPath, path)) {
                mCurrentPlayButtonClickListner.stopPlayVoice();
                return;
            }
            mCurrentPlayButtonClickListner.stopPlayVoice();
        }
        playButton.startRecordAnimation();
        playVoice(path);
        if (leftSide && ivIsListened != null) {
            ivIsListened.setVisibility(View.INVISIBLE);
            chatMessageBean.setIsListened(true);
//            context
            MyApplication.getInstance().getDaoSession().update(chatMessageBean);
        }
    }

    public void stopPlayVoice() {
        playButton.stopRecordAnimation();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        isPlaying = false;
    }

    public void playVoice(String filePath) {
        if (!(new File(filePath).exists())) {
            return;
        }
        mediaPlayer = new MediaPlayer();
        this.audioPath = path;
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mediaPlayer.release();
                    mediaPlayer = null;
                    stopPlayVoice(); // stop animation
                }

            });
            isPlaying = true;
            mCurrentPlayButtonClickListner = this;
            mediaPlayer.start();
            playButton.startRecordAnimation();
        } catch (Exception e) {
            System.out.println();
        }
    }


}
