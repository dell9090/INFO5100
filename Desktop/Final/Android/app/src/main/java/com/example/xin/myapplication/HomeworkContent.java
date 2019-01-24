package com.example.xin.myapplication;

public class HomeworkContent {
    private String title;
    private String description;
    private String opened_at;
    private String closed_at;

    HomeworkContent(String title, String description, String opened_at, String closed_at) {
        this.title = title;
        this.description = description;
        this.opened_at = opened_at;
        this.closed_at = closed_at;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getClosed_at() {
        return closed_at;
    }

    public String getOpened_at() {
        return opened_at;
    }
}
