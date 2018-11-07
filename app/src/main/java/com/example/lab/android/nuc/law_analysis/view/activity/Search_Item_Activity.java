package com.example.lab.android.nuc.law_analysis.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.LawListAdapter;
import com.example.lab.android.nuc.law_analysis.base.DataBean;
import com.example.lab.android.nuc.law_analysis.base.LawItemBean;
import com.example.lab.android.nuc.law_analysis.utils.SQLiteUtils;
import com.example.lab.android.nuc.law_analysis.view.customview.CustomStatusView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Search_Item_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyContent;
    private static TextView tipTextView;
    private static Dialog ProgressDialog;
    private  static CustomStatusView customStatusView;
    private LawListAdapter lawListAdapter;

    private String search_data;
    private String[] cutSearchData;
    private List<LawItemBean> laws;

    private Connection connection;//数据库连接操作变量
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent_item);
        initView();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            search_data = bundle.getString("data");
        }

        showCompleteDialog(Search_Item_Activity.this, "查找中");
    }

    void showCompleteDialog(final Context context, String msg){

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.shop_dialog_toast, null);// 得到加载view

        tipTextView = v.findViewById(R.id.tv_toast_content);// 提示文字
        tipTextView.setText(msg);// 设置加载信息
        customStatusView = v.findViewById(R.id.as_status);
        customStatusView.loadLoading();
        ProgressDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        ProgressDialog.setCancelable(false); // 是否可以按“返回键”消失
        ProgressDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        ProgressDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        /**
         *将显示Dialog的方法封装在这里面
         */
        final Window window = ProgressDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width =1000;
        lp.height =800;
        lp.y=-150;
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        ProgressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * 在此处更新recyclerView的ui
                 * */
                SQLiteDatabase sqLiteDatabase = null;
                try {
                    sqLiteDatabase = SQLiteUtils.getDatabase(context);

                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from LawArticleAll_set where law_from " +
                            "like '%" + search_data +"%' or law_content like '%" + search_data +"%'", null);

                    laws.clear();//清空列表
                    //随机案例
                    if (cursor.moveToFirst()) {
                        do {
                            String lawline = cursor.getString(cursor.getColumnIndex("law_line"));
                            String lawfrom = cursor.getString(cursor.getColumnIndex("law_from"))
                                    .replace(".txt", "");
                            String lawcontent = cursor.getString(cursor.getColumnIndex("law_content"));

                            laws.add(new LawItemBean(lawline, lawcontent, lawfrom));
                        } while (cursor.moveToNext());

                        customStatusView.loadSuccess();
                        if (!laws.isEmpty()){
                            emptyContent.setVisibility(View.GONE);
                        }
                        lawListAdapter.notifyDataSetChanged();
                        cursor.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    tipTextView.setText("获取推荐失败！请检查网络是否畅通！");
                    customStatusView.loadFailure();
                }
            }
        }, 1000);
        //这里用到了handler的定时器效果 延迟2秒执行dismiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDialog.dismiss();
            }
        }, 2500);
    }

    private void initView() {
        laws = new ArrayList<>();
        context = Search_Item_Activity.this;
        recyclerView = (RecyclerView)findViewById(R.id.search_recyclerView_ans);
        emptyContent = (TextView)findViewById(R.id.empty_content);

        initrecycleView();
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "搜索结果" );
        }
    }

    private void initrecycleView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(Search_Item_Activity.this));
        recyclerView.setLongClickable(true);
        lawListAdapter = new LawListAdapter(laws);
        lawListAdapter.setOnItemClickLitener(new LawListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(LawItemBean lawItemBean) {
            }

            @Override
            public void onItemLongClick(LawItemBean lawItemBean) {
                android.content.ClipboardManager cm = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cm.setText(lawItemBean.getLaw_line() + lawItemBean.getLaw_content() + lawItemBean.getLaw_from());
                Toast.makeText(Search_Item_Activity.this, "已复制到粘贴板中！", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(lawListAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
