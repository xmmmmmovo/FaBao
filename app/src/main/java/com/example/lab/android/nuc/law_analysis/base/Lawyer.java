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

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setLawyer_level(String lawyer_level) {
        this.lawyer_level = lawyer_level;
    }

    public String getLawyer_level() {
        return lawyer_level;
    }

    public void setLawyer_like(String lawyer_like) {
        this.lawyer_like = lawyer_like;
    }

    public String getLawyer_like() {
        return lawyer_like;
    }

    public void setLawyer_location(String lawyer_location) {
        this.lawyer_location = lawyer_location;
    }

    public String getLawyer_location() {
        return lawyer_location;
    }

    public String getLawyer_name() {
        return lawyer_name;
    }

    public void setLawyer_name(String lawyer_name) {
        this.lawyer_name = lawyer_name;
    }
}
