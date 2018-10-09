package com.example.lab.android.nuc.law_analysis.view.customview;

public class CardItem {

    private int mTextResource;
    private int mTitleResource;
    private int mText_coin;
    private int mImageView;


    public CardItem(int title, int text,int mText_coin,int mImageView) {
        mTitleResource = title;
        mTextResource = text;
        this.mImageView= mImageView;
        this.mText_coin = mText_coin;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }

    public int getmImageView(){
        return mImageView;
    }
    public int getmText_coin(){
        return mText_coin;
    }

}