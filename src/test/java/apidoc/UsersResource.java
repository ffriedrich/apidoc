package apidoc;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * User: frank
 */
@Path(value = "users")
public class UsersResource {

    @GET
    @Path("{userId}")
    @Produces("application/json")
    @Result(value = UserDto.class)
    public Response getUser(@PathParam("userId") @DefaultValue("4711") int userId) {
        return Response.ok(new UserDto()).build();
    }

    @POST
    @Path("{userId}/address")
    public void setAddress(@PathParam("userId") int userId, AddressDto address) {

    }
}


