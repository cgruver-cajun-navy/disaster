package org.labmonkeys.cajun_navy.disaster.colaborators.incident;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient
@Path("/incidents")
public interface IncidentApi {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<IncidentDTO> incidents();
    
    @GET
    @Path("/incident/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public IncidentDTO incidentById(@PathParam("id") String incidentId); 

}
