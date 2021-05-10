package quarkus.world.tour;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/rock")
public class RockBandResource {

    @GET
    public List<Band> getAll() {
        return Band.listAll();
    }

    @GET
    @Path("/alive")
    public List<Band> getAlive() {
        return Band.stillAlive();
    }

    @GET
    @Path("/{id}")
    public Band getOne(@PathParam("id") long id) {
        Band band = Band.findById(id);
        if (band == null) {
            throw new WebApplicationException(404);
        }
        return band;
    }

    @POST
    @Transactional
    public Response addOne(Band band) {
        band.persist();
        return Response.created(UriBuilder.fromPath("/band/" + band.id).build()).build();
    }

}
