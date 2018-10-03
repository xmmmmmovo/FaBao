package com.example.lab.android.nuc.law_analysis.utils.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.lab.android.nuc.law_analysis.R;

@SuppressLint("AppCompatCustomView")
public class HoverImageView extends ImageView {

    //以填满整个ImageView为目的，将原图的中心对准ImageView的中心，
    // 等比例放大原图，直到填满ImageView为止
    // （指的是ImageView的宽和高都要填满），
    // 原图超过ImageView的部分作裁剪处理。
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final PorterDuffXfermode duffMode = new PorterDuffXfermode( PorterDuff.Mode.SRC_IN);


    private boolean pressed;

    //创建
    private int borderColor = 0x44000000;
    private int hoverColor = 0x22000000;

    //创建画笔
    private Paint borderPaint;

    //创建路径
    private Path boundPath;
    private Path borderPath;

    private RectF rect = new RectF();

    //图片的范围
    private float borderWidth = 1f;


    public HoverImageView(Context context) {
        super(context);
        setup(null);
    }

    public HoverImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public HoverImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);

    }

    private void setup(AttributeSet attrs){
        if (attrs != null){
            @SuppressLint("CustomViewStyleable")
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            borderColor = typedArray.getColor(R.styleable.RoundImageView_borderColor,borderColor);
            hoverColor = typedArray.getColor(R.styleable.RoundImageView_hoverColor,hoverColor);
            borderWidth = typedArray.getDimension(R.styleable.RoundImageView_borderwidth,borderWidth);
            typedArray.recycle();
        }
        borderPath = new Path();
        boundPath = new Path();
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStrokeWidth(borderWidth);

        super.setScaleType(SCALE_TYPE);
    }

    @Override
    protected void onDetachedFromWindow() {
        setImageDrawable(null);
        super.onDetachedFromWindow();
    }


    protected void drawBorder(Canvas canvas){
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        canvas.drawPath(borderPath,borderPaint);
    }

    protected void drawHover(Canvas canvas) {
        if(this.isClickable() && pressed){
            borderPaint.setStyle(Paint.Style.FILL);
            borderPaint.setColor(hoverColor);
            canvas.drawPath(boundPath, borderPaint);
        }
    }

    public void buildBorderPath(Path borderPath) {
        borderPath.reset();
        final float halfBorderWidth = borderWidth * 0.5f;
        boundPath.addRect(halfBorderWidth, halfBorderWidth,
                getWidth() - halfBorderWidth, getHeight() - halfBorderWidth, Path.Direction.CW);
    }

    public void buildBoundPath(Path boundPath){
        boundPath.reset();
        boundPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(changed){
            buildBoundPath(boundPath);
            buildBorderPath(borderPath);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Drawable maiDrawable = getDrawable();
        if (!isInEditMode() && maiDrawable instanceof BitmapDrawable) {
            Paint paint = ((BitmapDrawable) maiDrawable).getPaint();

            Rect bitmapBounds = maiDrawable.getBounds();
            rect.set(bitmapBounds);

            @SuppressLint("WrongConstant") int saveCount = canvas.saveLayer(rect, null,
                    Canvas.MATRIX_SAVE_FLAG |
                            Canvas.CLIP_SAVE_FLAG |
                            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                            Canvas.CLIP_TO_LAYER_SAVE_FLAG);
            getImageMatrix().mapRect(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            final int color = 0xffffffff;
            paint.setColor(color);
            canvas.drawPath(boundPath, paint);

            Xfermode oldMode = paint.getXfermode();
            paint.setXfermode(duffMode);
            super.onDraw(canvas);
            paint.setXfermode(oldMode);
            canvas.restoreToCount(saveCount);

            drawHover(canvas);
            drawBorder(canvas);
        } else {
            super.onDraw(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean touched = super.onTouchEvent(event);
        if(touched){
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    pressed = true;
                    postInvalidate();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    pressed = false;
                    postInvalidate();
                    break;
                default:
                    break;
            }
        }
        return touched;
    }

    public float getBorderWidth() {
        return borderWidth;
    }


}
