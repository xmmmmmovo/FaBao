package com.example.lab.android.nuc.law_analysis.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.RecyclerViewAdapter;
import com.example.lab.android.nuc.law_analysis.base.People;
import com.example.lab.android.nuc.law_analysis.communication.viewholder.AdapterViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SelectArticleActivity extends AppCompatActivity {

    private RecyclerViewAdapter mArticleAdapter;
    private RecyclerView mRecyclerArticle;

    private void assignViews() {
        mRecyclerArticle = (RecyclerView) findViewById( R.id.recycler_article);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_article );
        assignViews();
        initIncomeRecyclerView();
        loadDate();
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "周边法制" );
        }
    }


    private void initIncomeRecyclerView() {
        mRecyclerArticle.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerArticle.setItemAnimator(null);
        mArticleAdapter = new RecyclerViewAdapter<People>( R.layout.item_recyle) {
            @Override
            public void convert(People model, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView(R.id.tv_name);
                RelativeLayout relativeLayout = holder.getView(R.id.rl_item);
                holder.setText(R.id.tv_name, model.getName());
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = SelectArticleActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString("article", textView.getText().toString());//添加要返回给页面1的数据
                        intent.putExtras(bundle);
                        SelectArticleActivity.this.setResult( Activity.RESULT_OK, intent);//返回页面1
                        SelectArticleActivity.this.finish();
                    }
                });
            }
        };
        mRecyclerArticle.setAdapter(mArticleAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("夫妻离婚 对有精神障碍的成年子女是否还有抚养义务");
        People people1 = new People();
        people1.setName("校园暴力事件追踪 打人者会受到什么处罚？");
        People people2 = new People();
        people2.setName("捡拾财物莫贪心 不当得利需返还");
        People people3 = new People();
        people3.setName("建筑施工要注意 灯光污染要赔偿");
        People people4 = new People();
        people4.setName("股东知情权被侵犯");
        People people5 = new People();
        people5.setName("如何认定擅自转载的作品是否属于时事性文章");
        People people6 = new People();
        people6.setName("无许可证就售房，被索要一倍赔偿");
        People people7 = new People();
        people7.setName("工作时间不固定 劳动者如何保障正常休息时");
        People people8 = new People();
        people8.setName("讨薪只领回3箱酒 以物抵债可不可以？");


        List<People> tempList = new ArrayList<>();
        tempList.add(people);
        tempList.add(people1);
        tempList.add(people2);
        tempList.add(people3);
        tempList.add(people4);
        tempList.add(people5);
        tempList.add(people6);
        tempList.add(people7);
        tempList.add(people8);
        mArticleAdapter.replaceAll(tempList);
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
