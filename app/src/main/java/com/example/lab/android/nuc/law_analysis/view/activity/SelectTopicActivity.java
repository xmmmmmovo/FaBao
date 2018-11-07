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


public class SelectTopicActivity extends AppCompatActivity {
    private RecyclerViewAdapter mTopicAdapter;

    private RecyclerView mRecyclerTopic;

    private void assignViews() {
        mRecyclerTopic = (RecyclerView) findViewById( R.id.recycler_topic);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic );
        assignViews();
        initIncomeRecyclerView();
        loadDate();
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "热门话题" );
        }
    }

    private void initIncomeRecyclerView() {
        mRecyclerTopic.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerTopic.setItemAnimator(null);
        mTopicAdapter = new RecyclerViewAdapter<People>(R.layout.item_recyle) {
            @Override
            public void convert(People people, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView( R.id.tv_name );
                RelativeLayout relativeLayout = holder.getView( R.id.rl_item );
                holder.setText( R.id.tv_name, people.getName() );
                relativeLayout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = SelectTopicActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString( "topic", textView.getText().toString() );//添加要返回给页面1的数据
                        intent.putExtras( bundle );
                        SelectTopicActivity.this.setResult( Activity.RESULT_OK, intent );//返回页面1
                        SelectTopicActivity.this.finish();
                    }
                } );
            }


        };
        mRecyclerTopic.setAdapter(mTopicAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("法学院校开学致辞");
        People people1 = new People();
        people1.setName("昆山砍人案被撤案");
        People people2 = new People();
        people2.setName("滴滴再出命案反思");
        People people3 = new People();
        people3.setName("怎样让人狗更和谐");
        People people4 = new People();
        people4.setName("现实版药神引思考");
        People people5 = new People();
        people5.setName("空姐遇害案");
        People people6 = new People();
        people6.setName("来法博看春景");
        People people7 = new People();
        people7.setName("医生常遇患者录音");
        People people8 = new People();
        people8.setName("张扣扣复仇案");

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
        mTopicAdapter.replaceAll(tempList);
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