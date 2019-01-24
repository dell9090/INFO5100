package com.example.xin.myapplication;

public class Homework {
    int id;
    String title;
    int attachment_id;
    private String description;
    private String open_at;
    private String close_at;

    Homework(int id, String title, int attachment_id, String description,
             String open_at, String close_at) {
        this.id = id;
        this.title = title;
        this.attachment_id = attachment_id;
        this.description = description;
        this.open_at = open_at;
        this.close_at = close_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAttachment_id(int attachment_id) {
        this.attachment_id = attachment_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClosed_at(String close_at) {
        this.close_at = close_at;
    }

    public void setOpened_at(String open_at) {
        this.open_at = open_at;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAttachment_id() {
        return attachment_id;
    }

    public String getClose_at() {
        return close_at;
    }

    public String getDescription() {
        return description;
    }

    public String getOpen_at() {
        return open_at;
    }
}
