package com.example.lab.android.nuc.law_analysis.utils.iflytek;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.util.ResourceUtil;

public class iflytekWakeUp {
    private Context mContext;
    //唤醒的阈值
    private int curThresh = 1500;
    private String keep_alive = "0";
    private String ivwNetMode = "0";

    private VoiceWakeuper mIvw;
    //存储唤醒词的ID
    private resultresolve listener;

    public iflytekWakeUp(Context context,resultresolve lis) {
        this.listener=lis;
        this.mContext = context;
        mIvw = VoiceWakeuper.createWakeuper(mContext, null);
    }
    public void startWakeuper() {
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            mIvw.setParameter( SpeechConstant.PARAMS, null);
            mIvw.setParameter( SpeechConstant.IVW_THRESHOLD, "0:" + curThresh);
            mIvw.setParameter( SpeechConstant.IVW_SST, "wakeup");
            mIvw.setParameter( SpeechConstant.KEEP_ALIVE, keep_alive);
            mIvw.setParameter( SpeechConstant.IVW_NET_MODE, ivwNetMode);
            mIvw.setParameter( SpeechConstant.IVW_RES_PATH, getResource());
            mIvw.startListening(new MyWakeuperListener() );
        }
    }

    private String getResource() {
        final String resPath = ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "ivw/" +
                "5bb37e54" + ".jet");
        return resPath;
    }
    /**
     * 销毁唤醒功能
     */
    public void destroyWakeuper() {
        // 销毁合成对象
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            mIvw.destroy();
        }
    }
    /**
     * 停止唤醒
     */
    public void stopWakeuper() {
        mIvw.stopListening();
    }
    /**
     * 唤醒词监听类
     *
     * @author Administrator
     */
    private class MyWakeuperListener implements com.iflytek.cloud.WakeuperListener {

        @Override
        public void onVolumeChanged(int arg0) {}
        //开始说话
        @Override
        public void onBeginOfSpeech() {}

        @Override
        public void onResult(com.iflytek.cloud.WakeuperResult wakeuperResult) {
            listener.resolveresult("waked");
        }

        //错误码返回
        @Override
        public void onError(SpeechError arg0) {}
        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

        }
    }

}
