package org.mycompany.jakarta.hello;

import java.time.LocalDate;
import java.util.List;

import org.mycompany.bewerbung.BewerbungFilter;
import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;
import org.mycompany.bewerbungssystem.infrastructure.persistence.repository.BewerbungRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationPath("/api") // Definiert den Basispfad
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BewerbungResource extends Application {

    @Inject
    BewerbungRepository repository;

    @GET
    public List<BewerbungEntity> getAll() {
        return repository.filterBewerbungen(new BewerbungFilter());
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return repository.findById(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response create(BewerbungEntity bewerbung) {
		BewerbungEntity saved = repository.persistAndFlush(bewerbung);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    @Path("/testfilter")
    public List<BewerbungEntity> testFilter() {
        BewerbungFilter filter = new BewerbungFilter();
        filter.setStatus("BEWORBEN");
        filter.setEingereichtVon(LocalDate.now().minusDays(7));
        filter.setEingereichtBis(LocalDate.now());
        
        return repository.filterBewerbungen(filter);
    }
}