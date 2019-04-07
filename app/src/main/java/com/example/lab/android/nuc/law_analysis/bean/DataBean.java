package com.example.lab.android.nuc.law_analysis.bean;

public class DataBean {

    private String Demo_textView ;
    private String Demo_Content;

    public DataBean(String textView){
        Demo_textView = textView;
    }

    public DataBean(String title, String content){
        Demo_textView = title;
        Demo_Content = content;
    }

    public String getDemo_textView() {
        return Demo_textView;
    }

    public void setDemo_textView(String demo_textView) {
        Demo_textView = demo_textView;
    }

    public String getDemo_Content() {
        return Demo_Content;
    }

    public void setDemo_Content(String demo_Content) {
        Demo_Content = demo_Content;
    }

}
