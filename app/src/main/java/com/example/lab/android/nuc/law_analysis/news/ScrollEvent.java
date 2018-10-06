package com.example.lab.android.nuc.law_analysis.news;

import android.widget.ScrollView;

public class ScrollEvent {
    private String id;
    public ScrollEvent(String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
