package apidoc;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * User: frank
 */
@Path(value = "users")
public class UsersResource {

    @GET
    @Path("{userId}")
    public Response getUser(@PathParam("userId") @DefaultValue("4711") int userId) {
        return Response.ok(new UserDto()).build();
    }
}
