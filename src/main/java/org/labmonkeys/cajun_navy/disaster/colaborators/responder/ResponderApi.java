package org.labmonkeys.cajun_navy.disaster.colaborators.responder;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@RegisterRestClient(configKey = "responder_api")
@Path("/")
@ApplicationScoped
public interface ResponderApi {

    @GET
    @Path("/responder/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponderDTO responder(@PathParam("id") String responderId);

    @GET
    @Path("/responder/byname/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponderDTO responderByName(@PathParam("name") String name);

    @GET
    @Path("/responders/available")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResponderDTO> availableResponders(@QueryParam("disasterId") Optional<String> disasterId, @QueryParam("limit") Optional<Integer> limit, @QueryParam("offset") Optional<Integer> offset);

    @GET
    @Path("/responders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResponderDTO> responders(@QueryParam("disasterId") Optional<String> disasterId, @QueryParam("limit") Optional<Integer> limit, @QueryParam("offset") Optional<Integer> offset);

    @POST
    @Path("/responder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponderDTO createResponder(ResponderDTO dto);

    @PUT
    @Path("/responder/info")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponderDTO updateResponderInfo(ResponderDTO dto);

    @PUT
    @Path("/responder/available")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponderDTO updateResponderAvailable(ResponderDTO dto);

    @PUT
    @Path("/responder/location")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponderDTO updateResponderLocation(ResponderDTO dto);

    @POST
    @Path("/responders")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResponders(List<ResponderDTO> dtos);

    @POST
    @Path("/responders/reset")
    public Response reset();

    @POST
    @Path("/responders/clear")
    public Response clear(@QueryParam("deleteBots") Optional<Boolean> delete);

    @POST
    @Path("/responders/clear/delete-bots")
    public Response clearAndDeleteBots();

    @POST
    @Path("/responders/clear/delete-all")
    public Response clearAndDeleteAll();
}
