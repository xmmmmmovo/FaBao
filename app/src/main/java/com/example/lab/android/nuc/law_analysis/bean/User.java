package com.example.lab.android.nuc.law_analysis.bean;

import android.widget.EditText;

public class User {
    private String name;
    private String password;

    public User() { }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
