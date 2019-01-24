package edu.neu.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user-management")
public class HomeworkService {
    private HomeworkDAO homeworkDAO = HomeworkDAO.getHomeworkDAO();

    @GET
    @Path("/homework/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HomeworkList getHomework(@PathParam("id") int id) {
        return homeworkDAO.getList(id);
    }

}
