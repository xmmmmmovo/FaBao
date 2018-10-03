package com.example.lab.android.nuc.law_analysis.utils.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.application.MyApplication;

/*
用于炫酷的Toolbar侧滑栏里面的每一个文字数据
 */
@SuppressLint("AppCompatCustomView")
public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface( MyApplication.canaroExtraBold);
    }

}
