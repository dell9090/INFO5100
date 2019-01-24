package edu.neu.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user-management")
public class CoursewareService {

    @GET
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("id") int id ) {
        return CoursewareDAO.download(id);
    }

    @GET
    @Path("/courseware/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CoursewareList getCourseware(@PathParam("id") int id) {
        return CoursewareDAO.getCourseware(id);
    }

}
