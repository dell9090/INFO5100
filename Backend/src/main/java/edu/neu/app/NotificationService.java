package edu.neu.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user-management")
public class NotificationService {
    private NotificationDAO notificationDAO = NotificationDAO.getNotificationDAO();

    @GET
    @Path("/notification/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NotificationList getUser(@PathParam("id") int id) {
        return notificationDAO.getNotifications(id);
    }

}
