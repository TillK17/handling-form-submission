package com.example.handling_form_submission;

public class Message {

    private long id;
    private String content;
    private String foundationmodel;

    public String getFoundationmodel() {
        return foundationmodel;
    }

    public void setFoundationmodel(String foundationmodel) {
        this.foundationmodel = foundationmodel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}