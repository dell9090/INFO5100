package edu.neu.app;

public class Notification {
    private int id;
    private int courseId;
    private String title;
    private String content;
    private String updated_at;

    Notification(int id, int courseId, String title, String content) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        System.out.println("time:"+updated_at);
        return updated_at;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
