import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Clock;
import java.time.LocalDateTime;


@Path("/counter")
public class CounterServlet{
    public static final String SUBTRACTION_HEADER_NAME = "Subtraction-Value";

    public static final String AUTH_NAME = "hh-auth";

    private Counter counter;

    private Clock clock;

    public CounterServlet(@Context ServletContext context) {
        counter = (Counter) context.getAttribute("counter");
        clock = (Clock) context.getAttribute("clock");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCounterValue(){
        ObjectNode node = new ObjectMapper().createObjectNode()
                .put("date", LocalDateTime.now(clock).toString())
                .put("counter", counter.get());
        return Response.ok(node).build();
    }

    @POST
    public Response incrementCounter(){
        counter.increment();
        return Response.ok().build();
    }

    @DELETE
    public Response subtractCounter(@HeaderParam(SUBTRACTION_HEADER_NAME) Integer subtractionValue){
        if (subtractionValue == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad header value").build();
        }
        counter.decrementByValue(subtractionValue);
        return Response.ok().build();
    }

    @POST
    @Path("/clear")
    public Response clearCounter(@CookieParam(AUTH_NAME) String auth){
        if (auth == null || auth.length() <= 10){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Bad auth cookie").build();
        }
        counter.reset();
        return Response.ok().build();
    }
}
