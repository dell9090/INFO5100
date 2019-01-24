package edu.neu.app;

public class SimpleComment {
    int post_id;
    int page;
    int subjectid;
    SimpleComment() {

    }
    SimpleComment(int post_id, int page, int subjectid) {
        this.post_id = post_id;
        this.page = page;
        this.subjectid = subjectid;
    }

    public int getPage() {
        return page;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }
}
