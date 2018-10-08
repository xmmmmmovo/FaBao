package com.example.lab.android.nuc.law_analysis.base;

public class Law {

    public static String account;
    public static String password;
    private String Law_name;

    private String Law_image;

    private String Law_uri;

    public Law(String name,String image,String uri ){
        this.Law_name = name;
        this.Law_image = image;
        this.Law_uri = uri;
    }

    public String getLaw_image() {
        return Law_image;
    }

    public void setLaw_image(String law_image) {
        Law_image = law_image;
    }

    public String getLaw_name() {
        return Law_name;
    }

    public void setLaw_name(String law_name) {
        Law_name = law_name;
    }

    public String getLaw_uri() {
        return Law_uri;
    }

    public void setLaw_uri(String law_uri) {
        Law_uri = law_uri;
    }
}
