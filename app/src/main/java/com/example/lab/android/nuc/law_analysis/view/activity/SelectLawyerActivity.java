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

public class SelectLawyerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerAiTe;
    private RecyclerViewAdapter mAiTeAdapter;

    private void assignViews() {
        mRecyclerAiTe = (RecyclerView) findViewById( R.id.recycler_ai_te);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lawyer );
        assignViews();
        initIncomeRecyclerView();
        loadDate();

        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "选择律师" );
        }
    }


    private void initIncomeRecyclerView() {
        mRecyclerAiTe.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAiTe.setItemAnimator(null);
        mAiTeAdapter = new RecyclerViewAdapter<People>(R.layout.item_recyle) {
            @Override
            public void convert(People model, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView(R.id.tv_name);
                RelativeLayout relativeLayout = holder.getView(R.id.rl_item);
                holder.setText(R.id.tv_name, model.getName());
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = SelectLawyerActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString("ai_te", textView.getText().toString());//添加要返回给页面1的数据
                        intent.putExtras(bundle);
                        SelectLawyerActivity.this.setResult( Activity.RESULT_OK, intent);//返回页面1
                        SelectLawyerActivity.this.finish();
                    }
                });
            }
        };
        mRecyclerAiTe.setAdapter(mAiTeAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("白云华   --  特级律师");
        People people1 = new People();
        people1.setName("萧晓林  --  二级律师");
        People people2 = new People();
        people2.setName("胡建国  --  一级律师");
        People people3 = new People();
        people3.setName("陈克刚  -- 特级律师");
        People people4 = new People();
        people4.setName("张世豪 --  初级律师");
        People people5 = new People();
        people5.setName("万正学  --  二级律师");
        People people6 = new People();
        people6.setName("李星亮  --  初级律师");
        People people7 = new People();
        people7.setName("郎坚  --  二级律师");
        People people8 = new People();
        people8.setName("王立建  --  初级律师");
        People people9 = new People();
        people9.setName("张连民  --  特级律师");

        List<People> tempList = new ArrayList<>();
        tempList.add(people);
        tempList.add(people1);
        tempList.add(people2);
        tempList.add( people3 );
        tempList.add( people4 );
        tempList.add( people5 );
        tempList.add( people6 );
        tempList.add( people7 );
        tempList.add( people8 );
        tempList.add( people9 );
        mAiTeAdapter.replaceAll(tempList);
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