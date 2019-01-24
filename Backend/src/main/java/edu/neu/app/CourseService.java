package edu.neu.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user-management")
public class CourseService {
    private CourseDAO courseDAO = CourseDAO.getCourseDAO();

    @GET
    @Path("/course/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseList getUser(@PathParam("id") int id) {
        return courseDAO.getCourse(id);
    }

}
