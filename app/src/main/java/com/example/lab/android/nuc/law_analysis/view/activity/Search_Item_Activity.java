package com.example.lab.android.nuc.law_analysis.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.R;

public class Search_Item_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent_item);
        TextView textView =(TextView) findViewById(R.id.tv_info);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            textView.setText(bundle.getString("data"));
        }

    }
}
