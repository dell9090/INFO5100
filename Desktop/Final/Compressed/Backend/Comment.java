package edu.neu.app;

public class Comment {
    private int id;
    private int usrid;//usrid
    private int subjectid;
    private int post_type;
    private int post_id; //pdf_id
    private int page;
    private int top;
    private String content;
    private int agreecount;
    private int ifagree;

    public Comment() {

    }

    public Comment(int id, int usrid, int subjectid, int post_type, int post_id,
                   int page, int top, String content, int agreecount, int ifagree) {
        this.id = id;
        this.usrid = usrid;
        this.subjectid = subjectid;
        this.post_type = post_type;
        this.post_id = post_id;
        this.page = page;
        this.top = top;
        this.content = content;
        this.agreecount = agreecount;
        this.ifagree = ifagree;
    }

    public int getIfagree() {
        return ifagree;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public int getId() {
        return id;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getPage() {
        return page;
    }

    public String getContent() {
        return content;
    }

    public int getPost_type() {
        return post_type;
    }

    public int getTop() {
        return top;
    }

    public int getAgreecount() {
        return agreecount;
    }

    public int getUsrid() {
        return usrid;
    }

    public void setIfagree(int ifagree) {
        this.ifagree = ifagree;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

    public void setAgreecount(int agreecount) {
        this.agreecount = agreecount;
    }

    public void setUsrid(int usrid) {
        this.usrid = usrid;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
