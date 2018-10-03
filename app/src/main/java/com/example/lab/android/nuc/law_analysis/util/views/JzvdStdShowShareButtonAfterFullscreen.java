package com.example.lab.android.nuc.law_analysis.util.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;

import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;

public class JzvdStdShowShareButtonAfterFullscreen extends JzvdStd {
    public ImageView shareButton;

    public JzvdStdShowShareButtonAfterFullscreen(Context context) {
        super(context);
    }

    public JzvdStdShowShareButtonAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        shareButton = findViewById( R.id.share );
        shareButton.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_standard_with_share_button;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.share) {
            Toast.makeText(getContext(), "Whatever the icon means", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            shareButton.setVisibility(View.VISIBLE);
        } else {
            shareButton.setVisibility(View.INVISIBLE);
        }
    }
}
