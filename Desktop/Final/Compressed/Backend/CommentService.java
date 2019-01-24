package edu.neu.app;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user-management")
public class CommentService {
    private CommentDAO commentDAO = CommentDAO.getCommentDAO();


    @POST
    @Path("/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommentList getCommentList(SimpleComment s) {
        return commentDAO.getComments(s);
    }

    @POST
    @Path("/comment/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommentList createComment(Comment c) {
        return commentDAO.createComment(c);
    }

    @POST
    @Path("/comment/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CommentList updateComment(Comment c) {
        return commentDAO.updateComment(c);
    }

}
