package com.example.lab.android.nuc.law_analysis.utils.views;

import android.content.Context;

/*
此类是二哥的那个上面滑动的粘连点
 */
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class ColorFlipPagerTitleView extends SimplePagerTitleView {
    private float mChangePercent = 0.5f;
    public ColorFlipPagerTitleView(Context context) {
        super( context );
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (leavePercent >= mChangePercent){
            setTextColor( mNormalColor );
        }else {
            setTextColor( mSelectedColor );
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (enterPercent >= mChangePercent){
            setTextColor( mSelectedColor );
        }else {
            setTextColor( mNormalColor );
        }
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected( index, totalCount );
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected( index, totalCount );
    }

    public float getChangePercent() {
        return mChangePercent;
    }

    public void setChangePercent(float changePercent) {
        mChangePercent = changePercent;
    }
}
