package com.example.lab.android.nuc.law_analysis.news.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

public class CustomAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat( view ,"scaleY",1,1.1f,1),
                ObjectAnimator.ofFloat( view,"scaleX",1,1.1f,1 )
        };
    }
}
