package com.example.lab.android.nuc.law_analysis.util.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.lab.android.nuc.law_analysis.R;

public class RoundImageView extends HoverImageView {
    //定义方形圆弧的弧度
    private float radius = 10;


    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);

    }

    protected void setup(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundImageView);

        //在HoverImageView的基础上添加了radius角度
        radius = typedArray.getDimension( R.styleable.RoundImageView_borderRadius,radius);

        //回收TypedArray，以便后面重用
        typedArray.recycle();
    }

    @Override
    public void buildBoundPath(Path boundPath) {
        boundPath.reset();

        final int width = getWidth();
        final  int height = getHeight();

        //根据屏幕宽度高度计算角度
        radius = Math.min(radius,Math.min(width,height) * 0.5f);

        RectF rect = new RectF(0,0,width,height);

        boundPath.addRoundRect(rect,radius,radius, Path.Direction.CW);

    }

    @Override
    public void buildBorderPath(Path borderPath) {
        borderPath.reset();

        final float halfBorderWidth = getBorderWidth() * 0.5f;

        final int width = getWidth();
        final int height = getHeight();
        radius = Math.min(radius, Math.min(width, height) * 0.5f);

        RectF rect = new RectF(halfBorderWidth, halfBorderWidth,
                width - halfBorderWidth, height - halfBorderWidth);
        borderPath.addRoundRect(rect , radius, radius, Path.Direction.CW);
    }
}
