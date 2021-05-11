package quarkus.world.tour;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("alive")
    public List<Band> getAlive() {
        return Band.stillAlive();
    }

    @GET
    @Path("{id}")
    public Band getOne(@PathParam("id") long id) {
        final Band band = Band.findById(id);
        if (band == null) {
           throw new NotFoundException(id + " was not found");
        }
        return band;
    }

    @POST
    @Transactional
    public Response addOne(Band band) {
        band.persist();
        return Response.created(UriBuilder.fromPath("/rock/" + band.id).build()).build();
    }

}
