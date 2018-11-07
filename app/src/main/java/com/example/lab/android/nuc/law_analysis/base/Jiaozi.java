package com.example.lab.android.nuc.law_analysis.base;

public class Jiaozi {
    private String title,url;

    public Jiaozi(String title,String url){
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
