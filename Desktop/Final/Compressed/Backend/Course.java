package edu.neu.app;

public class Course {
    private int id;
    private int courseid;
    private String title;

    Course(int id, int courseid, String title) {
        this.courseid = courseid;
        this.id = id;
        this.title = title;
    }

    public int getCourseid() {
        return courseid;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public void setId(int id) {
        this.id = id;
    }
}
