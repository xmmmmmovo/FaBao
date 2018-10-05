package com.example.lab.android.nuc.law_analysis.news.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public abstract class AbsListAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public AbsListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }
}
