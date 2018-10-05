package com.example.lab.android.nuc.law_analysis.utils.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;

@SuppressLint("AppCompatCustomView")
public class RippleButtonView extends Button {
    int[] mLocation = new int[2];
    ///定义点击位置的x和y坐标
    float mCenterX;
    float mCenterY;
    float mRevealRadius;
    boolean mIsPressed = false;


    boolean mShouldDoAnimation = false;
    //定义画笔
    Paint mPaint = new Paint(  );

    public RippleButtonView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    private boolean isValidClick(float x,float y){
        //点击位置的坐标在button的范围之内
        if (x > 0 && x < getWidth() && y > 0 && y < getHeight()){
            return true;
        }
        return false;
    }

    //定义button事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if (!isValidClick( event.getX(),event.getY() )){
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (!isValidClick( event.getX(),event.getY() )){
                    return false;
                }
                mCenterX = event.getX();
                mCenterY = event.getY();
                mRevealRadius = 0f;

                mShouldDoAnimation = true;
                setFollowed( !mIsPressed,mShouldDoAnimation );
                return true;
        }
        return false;
    }

    protected void setFollowed(boolean isFollowed,boolean needAnimate){
        mIsPressed = isFollowed;
        if (needAnimate){
            @SuppressLint("ObjectAnimatorBinding")
            ValueAnimator animator = ObjectAnimator.ofFloat( this,"empty",0.0f,
                    (float)Math.hypot( getMeasuredWidth(),getMeasuredHeight()));
            animator.setDuration( 500L );
            animator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRevealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }
            } );
            animator.addListener( new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mIsPressed) {
                        setTextColor( Color.GRAY);
                        setBackgroundColor(Color.WHITE);
                        setText("√ 已关注");
                        Toast.makeText( getContext(), "已关注", Toast.LENGTH_SHORT ).show();
                        setBackgroundResource( R.drawable.button_guanzhu_1 );
                    } else {
                        setTextColor(Color.WHITE);
                        setBackgroundColor(Color.GREEN);
                        setText("+ 关注");
                        Toast.makeText( getContext(), "已取消关注", Toast.LENGTH_SHORT ).show();
                        setBackgroundResource( R.drawable.button_guanzhu );

                    }
                    mShouldDoAnimation = false;
                    mRevealRadius = 0;
                    invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            } );
            animator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
        if (!mIsPressed){
            mPaint.setColor( Color.GREEN );
        }else {
            mPaint.setColor( Color.WHITE );
        }//设置画笔颜色
        mPaint.setStyle( Paint.Style.FILL );
        canvas.drawCircle( mCenterX,mCenterY,mRevealRadius,mPaint );
    }
}
