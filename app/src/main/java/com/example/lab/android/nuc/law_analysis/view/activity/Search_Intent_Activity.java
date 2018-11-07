package com.example.lab.android.nuc.law_analysis.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.communication.activity.DictationResult;
import com.example.lab.android.nuc.law_analysis.communication.utils.KeyBoardUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.tangguna.searchbox.library.cache.HistoryCache;
import com.tangguna.searchbox.library.callback.onSearchCallBackListener;
import com.tangguna.searchbox.library.widget.SearchLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search_Intent_Activity extends AppCompatActivity {

    private SearchLayout searchLayout;
    private Button mYuYinButton;
    private EditText search_edit;
    private RecognizerDialog iatDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent_acvtivity);
        searchLayout = findViewById(R.id.searchlayout);
        search_edit = findViewById(R.id.et_searchtext_search);

        mYuYinButton = findViewById(R.id.bt_yuyin);
        mYuYinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice_to_text();
            }
        });
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "法律条目搜索" );
        }
        initData();
    }

    private void initData() {
        List<String> skills = HistoryCache.toArray(getApplicationContext());
        String shareHotData ="中华人民共和国婚姻法,中华人民共和国农业法,中华人民共和国民事诉讼法,中华人民共和国价格法";
        List<String> skillHots = Arrays.asList(shareHotData.split(","));
        searchLayout.initData(skills, skillHots, new onSearchCallBackListener() {
            @Override
            public void Search(String str) {
                //进行或联网搜索
                Log.e("点击",str.toString());
                Bundle bundle = new Bundle();
                bundle.putString("data",str);
                startActivity(Search_Item_Activity.class,bundle);
            }
            @Override
            public void Back() {
                finish();
            }

            @Override
            public void ClearOldData() {
                //清除历史搜索记录  更新记录原始数据
                HistoryCache.clear(getApplicationContext());
                Log.e("点击","清除数据");
            }
            @Override
            public void SaveOldData(ArrayList<String> AlloldDataList) {
                //保存所有的搜索记录
                HistoryCache.saveHistory(getApplicationContext(), HistoryCache.toJsonArray(AlloldDataList));
                Log.e("点击","保存数据");
            }
        },1);
    }



    public void startActivity(Class<?> openClass, Bundle bundle) {
        Intent intent = new Intent(this,openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (search_edit != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(search_edit, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (search_edit != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search_edit.getWindowToken(), 0);
        }
    }


    /*语音转文字*/
    public void voice_to_text(){
        // 有交互动画的语音识别器
        iatDialog = new RecognizerDialog(this, mInitListener);

        iatDialog.setListener(new RecognizerDialogListener() {
            String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                System.out.println("-----------------   onResult   -----------------");
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    String result = "";
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    search_edit.setText(result);
                    //获取焦点
                    search_edit.requestFocus();
                    //将光标定位到文字最后，以便修改
                    search_edit.setSelection(result.length());
                    if (findViewById(R.id.cbv_other) != null) {
                        findViewById( R.id.cbv_other ).setVisibility( View.GONE );
                    }
                    KeyBoardUtils.showKeyBoard(Search_Intent_Activity.this,search_edit );
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                //自动生成的方法存根
                speechError.getPlainDescription(true);
            }
        });
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    /**
     * 用于SpeechRecognizer（无交互动画）对象的监听回调
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.i(TAG, recognizerResult.toString());
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    public static final String TAG = "MainActivity";
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(Search_Intent_Activity.this, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
