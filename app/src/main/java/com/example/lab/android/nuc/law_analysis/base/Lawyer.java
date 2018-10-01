package com.example.lab.android.nuc.law_analysis.base;

public class Lawyer {
    public String lawyer_name,lawyer_level,lawyer_location,lawyer_like,imageUri;
    public Lawyer(String lawyer_name,String lawyer_level,String lawyer_location,String lawyer_like,String imageUri){
        this.lawyer_name = lawyer_name;
        this.lawyer_level = lawyer_level;
        this.lawyer_location = lawyer_location;
        this.lawyer_like = lawyer_like;
        this.imageUri = imageUri;
    }
}
