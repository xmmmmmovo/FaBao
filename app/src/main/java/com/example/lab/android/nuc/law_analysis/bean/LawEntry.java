package com.example.lab.android.nuc.law_analysis.bean;

public class LawEntry {
    private String law_line;
    private String law_content;
    private String law_from;

    public LawEntry(String law_line, String law_content, String law_from) {
        this.law_line = law_line;
        this.law_content = law_content;
        this.law_from = law_from;
    }

    public String getLaw_line() {
        return law_line;
    }

    public void setLaw_line(String law_line) {
        this.law_line = law_line;
    }

    public String getLaw_content() {
        return law_content;
    }

    public void setLaw_content(String law_content) {
        this.law_content = law_content;
    }

    public String getLaw_from() {
        return law_from;
    }

    public void setLaw_from(String law_from) {
        this.law_from = law_from;
    }
}
