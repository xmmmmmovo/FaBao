package com.example.lab.android.nuc.law_analysis.utils.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

public class IflytekSpeech {
    private Context mContext;
    private SpeechSynthesizer mTts;
    private resultresolve listener;
    public IflytekSpeech(Context context,resultresolve lis) {
        this.listener=lis;
        this.mContext = context;
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        mTts=SpeechSynthesizer.getSynthesizer();
        mTts.setParameter( SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.PITCH, "30");
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        mTts.setParameter(SpeechConstant.LANGUAGE,"EN");
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
    }
    public void Speek(String str){
        int code = mTts.startSpeaking(str, mSynListener);
        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(mContext, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            listener.resolveresult("speechover");
        }
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }
        public void onSpeakBegin() {
        }
        public void onSpeakPaused() {
        }
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }
        public void onSpeakResumed() {
        }
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}
