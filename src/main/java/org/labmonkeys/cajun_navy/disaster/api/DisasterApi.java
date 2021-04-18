package org.labmonkeys.cajun_navy.disaster.api;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.service.DisasterService;

@Path("/disaster")
@ApplicationScoped
public class DisasterApi {
    
    @Inject
    DisasterService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisasters() {
        return Response.ok(service.getDisasters()).build();
    }

    @GET
    @Path("/info/{disasterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisasterInfo(@PathParam("disasterId") Long disasterId) {
        return Response.ok(service.getDisasterInfo(disasterId)).build();
    }

    @GET
    @Path("/detail/{disasterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisasterDetail(@PathParam("disasterId") Long disasterId) {
        return Response.ok(service.getDisasterDetails(disasterId)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerDisaster(DisasterDTO disaster) {
        return Response.ok(service.registerDisaster(disaster)).build();
    }
    
    @DELETE
    @Path("/{disasterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeDisaster(@PathParam("disasterId") Long disasterId) {
        service.removeDisaster(disasterId);
        return Response.ok().build();
    }

    @POST
    @Path("/zone/{disasterId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInclusionZone(@PathParam("disasterId") Long disasterId, InclusionZoneDTO dto) {
        return Response.ok(service.addInclusionZone(dto, disasterId)).build();
    }

    @DELETE
    @Path("/zone/{zoneName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInclusionZone(@PathParam("zoneName") String zoneName) {
        service.deleteInclusionZone(zoneName);
        return Response.ok().build();
    }

    @POST
    @Path("/shelter/{disasterId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShelter(@PathParam("disasterId") Long disasterId, ShelterDTO dto) {
        return Response.ok(service.addShelter(dto, disasterId)).build();
    }

    @PUT
    @Path("/shelter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateShelter(ShelterDTO dto) {
        return Response.ok(service.updateShelter(dto)).build();
    }

    @DELETE
    @Path("/shelter/{shelterName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteShelter(@PathParam("shelterName") String shelterName) {
        service.deleteShelter(shelterName);
        return Response.ok().build();
    }
}
