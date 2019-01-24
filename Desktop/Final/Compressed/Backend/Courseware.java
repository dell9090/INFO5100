package edu.neu.app;

public class Courseware {
    private int id;
    private int courseId;
    private String dir;
    private String info;
    private String name;

    Courseware(int id, int courseId, String dir, String info, String name) {
        this.id = id;
        this.courseId = courseId;
        this.dir = dir;
        this.info = info;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
