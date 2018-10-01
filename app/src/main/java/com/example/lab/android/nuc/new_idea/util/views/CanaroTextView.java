package com.example.lab.android.nuc.new_idea.util.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.lab.android.nuc.new_idea.application.App;

public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface( App.canaroExtraBold);
    }

}
