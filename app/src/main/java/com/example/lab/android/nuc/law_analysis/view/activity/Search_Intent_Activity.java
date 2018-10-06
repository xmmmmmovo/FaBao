package com.example.lab.android.nuc.law_analysis.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;
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
                Toast.makeText(Search_Intent_Activity.this, "你想通过语音输入。。。。。。。。", Toast.LENGTH_SHORT).show();
            }
        });

        initData();
    }

    private void initData() {
        List<String> skills = HistoryCache.toArray(getApplicationContext());
        String shareHotData ="婚姻法,未成年人保护法,产权法,交通法";
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
}
