package quarkus.world.tour;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/rock")
public class RockBandResource {

    @GET
    public Multi<Band> getAll() {
        return Band.streamAll();
    }

    @GET
    @Path("/alive")
    public Multi<Band> getAlive() {
        return Band.stillAlive();
    }

    @GET
    @Path("/{id}")
    public Uni<Band> getOne(@PathParam("id") long id) {
        System.out.printf("Thread: %s\n", Thread.currentThread().getName());
        Uni<Band> uni = Band.findById(id);
        return uni
                .onItem().ifNull().failWith(new WebApplicationException(404));
    }

    @GET
    @Path("/{id}/blocking")
    @Blocking
    public Band getOneBlocking(@PathParam("id") long id) {
        System.out.printf("Thread: %s\n", Thread.currentThread().getName());
        Uni<Band> band = Band.findById(id);
        return band.await().indefinitely();
    }

    @POST
    public Uni<Band> addOne(Band band) {
        return Panache.withTransaction(band::persistAndFlush)
                .replaceWith(band);
    }

}
