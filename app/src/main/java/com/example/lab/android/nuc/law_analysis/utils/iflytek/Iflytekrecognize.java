package com.example.lab.android.nuc.law_analysis.utils.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.communication.activity.DictationResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import java.util.List;

public class Iflytekrecognize {
    private SpeechRecognizer mIat;
    private Context mcontext;
    private resultresolve listener;
    public Iflytekrecognize(Context context,resultresolve lis)
    {
        this.listener=lis;
        mcontext=context;
        mIat = SpeechRecognizer.createRecognizer(mcontext, mInitListener);
        // 2.设置听写参数
        mIat.setParameter( SpeechConstant.DOMAIN, "iat"); // domain:域名
        mIat.setParameter( SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter( SpeechConstant.ACCENT, "mandarin"); // mandarin:普通话
    }
    public void listening(){

        mIat= SpeechRecognizer.getRecognizer();
        mIat.startListening(new RecognizerListener() {
            String resultJson = "[";
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {}
            @Override
            public void onBeginOfSpeech() {}
            @Override
            public void onEndOfSpeech() {}
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }
                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    String result="";
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    listener.resolveresult(result);
                }
            }
            @Override
            public void onError(SpeechError speechError) {
                speechError.getPlainDescription(true);
            }
            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {
            }
        });
    }
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mcontext, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
