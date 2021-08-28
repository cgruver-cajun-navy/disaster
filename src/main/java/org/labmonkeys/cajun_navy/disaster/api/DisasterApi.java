package org.labmonkeys.cajun_navy.disaster.api;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;

@Path("/disaster")
@ApplicationScoped
public class DisasterApi {
    
    @Inject
    EventBus bus;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDisasters() {
        return bus.<List<DisasterDTO>>request("get-disasters", null).onItem().transform(msg -> Response.ok(msg.body()).build());
        //return Response.ok(service.getDisasters()).build();
    }

    @GET
    @Path("/info/{disasterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDisasterInfo(@PathParam("disasterId") UUID disasterId) {
        return bus.<DisasterDTO>request("get-disaster-info", disasterId).onItem().transform(msg -> Response.ok(msg.body()).build());
    }

    @GET
    @Path("/detail/{disasterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDisasterDetail(@PathParam("disasterId") UUID disasterId) {
        return bus.<DisasterDTO>request("get-disaster-detail", disasterId).onItem().transform(msg -> Response.ok(msg.body()).build());
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> registerDisaster(DisasterDTO disaster) {
        return bus.<DisasterDTO>request("register-disaster", disaster).onItem().transform(msg -> Response.ok(msg.body()).build());
    }
    
    @DELETE
    @Path("/{disasterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> removeDisaster(@PathParam("disasterId") UUID disasterId) {
        return bus.request("remove-disaster", disasterId).onItem().transform(msg -> Response.ok().build());
    }

    @POST
    @Path("/zone/{disasterId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> addInclusionZone(@PathParam("disasterId") UUID disasterId, InclusionZoneDTO dto) {
        ImmutablePair<InclusionZoneDTO, UUID> pair = new ImmutablePair<InclusionZoneDTO, UUID>(dto, disasterId);
        return bus.<DisasterDTO>request("add-inclusion-zone", pair).onItem().transform(msg -> Response.ok(msg.body()).build());
        //return Response.ok(service.addInclusionZone(dto, disasterId)).build();
    }

    @DELETE
    @Path("/zone/{zoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteInclusionZone(@PathParam("zoneId") UUID zoneId) {
        return bus.request("remove-inclusion-zone", zoneId).onItem().transform(msg -> Response.ok().build());
    }

    @POST
    @Path("/shelter/{disasterId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> addShelter(@PathParam("disasterId") UUID disasterId, ShelterDTO dto) {
        ImmutablePair<ShelterDTO, UUID> pair = new ImmutablePair<ShelterDTO,UUID>(dto, disasterId);
        return bus.<DisasterDTO>request("add-shelter", pair).onItem().transform(msg -> Response.ok(msg.body()).build());
    }

    @PUT
    @Path("/shelter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateShelter(ShelterDTO dto) {
        return bus.<DisasterDTO>request("update-shelter", dto).onItem().transform(msg -> Response.ok(msg.body()).build());
    }

    @DELETE
    @Path("/shelter/{shelterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteShelter(@PathParam("shelterId") UUID shelterId) {
        return bus.request("remove-shelter", shelterId).onItem().transform(msg -> Response.ok().build());
    }

    @GET
    @Path("/victim")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getVictimById(@QueryParam("disasterId") UUID disasterId, @QueryParam("shelterId") UUID shelterId,@QueryParam("victimId") UUID victimId) {
        Triple<UUID, UUID, UUID> triple = new ImmutableTriple<UUID,UUID,UUID>(disasterId, shelterId, victimId);
        return bus.request("get-victim", triple).onItem().transform(msg -> Response.ok(msg.body()).build());
    }
}
