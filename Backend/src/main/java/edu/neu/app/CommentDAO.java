package edu.neu.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommentDAO {
    private static CommentDAO unique;
    private static BaseDAO db = null;

    static CommentDAO getCommentDAO() {
        if (unique == null) {
            unique = new CommentDAO();
        }
        return unique;
    }

    public CommentList getComments(SimpleComment s) {
        String sql = "SELECT * FROM Comments WHERE courseid = " + s.getSubjectid() +
                " AND page = " + s.getPage() + " AND post_id = " + s.getPost_id();

        CommentList commentList = new CommentList();
        List<Comment> temp = new ArrayList<>();
        int count = 0;
        db = new BaseDAO();
        ResultSet rs = null;
        try {
            rs = db.query(sql);// 执行语句，得到结果集
            // 显示数据
            while(rs.next()) {
                int id = rs.getInt("id");
                int userid = rs.getInt("userid");
                int postType = rs.getInt("post_type");
                int agreecount = rs.getInt("agreecount");
                int top = rs.getInt("top");
                String content= rs.getString("content");
                int ifagree = rs.getInt("ifagree");
                Comment c = new Comment(id, userid, s.getSubjectid(), postType, s.getPost_id(),
                        s.getPage(), top, content, agreecount, ifagree);
                temp.add(c);
                count++;
            }
            commentList.setCount(count);
            commentList.setComments(temp);
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return commentList;
    }

    public CommentList createComment(Comment c) {
        String update = "INSERT INTO Comments(userid, courseid, post_type, post_id, page, top, content, agreecount)"
                + " VALUES (" +  c.getUsrid() + "," + c.getSubjectid() + "," +
                c.getPost_type() + "," + c.getPost_id() + "," + c.getPage() + "," + c.getTop() + ",'" +
                c.getContent() + "'," + c.getAgreecount() + ")";

        CommentList commentList = new CommentList();
        List<Comment> temp = new ArrayList<>();
        db = new BaseDAO();

        try {
            db.change(update);
            commentList.setCount(1);
            commentList.setComments(temp);
            db.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return commentList;
    }

    public CommentList updateComment(Comment c) {
        String update = "UPDATE Comments SET agreecount = " + c.getAgreecount() +
                ", ifagree = " + c.getIfagree() + " WHERE id = " + c.getId();

        CommentList commentList = new CommentList();
        List<Comment> temp = new ArrayList<>();
        db = new BaseDAO();

        try {
            db.change(update);
            commentList.setCount(1);
            commentList.setComments(temp);
            db.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return commentList;
    }
}
