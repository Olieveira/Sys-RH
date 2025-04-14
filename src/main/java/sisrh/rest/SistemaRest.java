package sisrh.rest;


import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Api
@Path("sistema")
public class SistemaRest {

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        UUID uuid = UUID.randomUUID();
        return Response.ok().entity("pong: " + uuid).build();
    }

    @GET
    @Path("datahora")
    @Produces(MediaType.TEXT_PLAIN)
    public Response datahora() {
        String pattern = "dd:MM:YYYY - HH:mm:ss";
        SimpleDateFormat dataSimples = new SimpleDateFormat(pattern);
        return Response.ok().entity(dataSimples.format(new Date())).build();
    }

}


