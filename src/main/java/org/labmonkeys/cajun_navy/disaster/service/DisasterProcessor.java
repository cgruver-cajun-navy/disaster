package org.labmonkeys.cajun_navy.disaster.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.labmonkeys.cajun_navy.disaster.colaborators.incident.IncidentApi;
import org.labmonkeys.cajun_navy.disaster.colaborators.incident.IncidentDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.incident.IncidentPublisher;
import org.labmonkeys.cajun_navy.disaster.colaborators.incident.VictimDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.mission.LocationDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.mission.MissionDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.mission.MissionPublisher;
import org.labmonkeys.cajun_navy.disaster.colaborators.responder.ResponderApi;
import org.labmonkeys.cajun_navy.disaster.colaborators.responder.ResponderDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.mapper.DisasterMapper;
import org.labmonkeys.cajun_navy.disaster.model.Disaster;
import org.labmonkeys.cajun_navy.disaster.model.RescuedVictim;
import org.labmonkeys.cajun_navy.disaster.model.Shelter;

@ApplicationScoped
public class DisasterProcessor {

    @Inject
    @RestClient
    ResponderApi responderApi;

    @Inject
    @RestClient
    IncidentApi incidentApi;

    @Inject
    DisasterMapper mapper;

    @Inject
    MissionPublisher missionPublisher;

    @Inject
    IncidentPublisher incidentPublisher;
    
    public void assignIncident(IncidentDTO incident) {
        // Get available shelters
        List<ShelterDTO> shelters = mapper.sheltersToDtos(Disaster.<Disaster>findById(incident.getDisasterId()).getShelters());
        // Get available responders
        List<ResponderDTO> responders = responderApi.availableResponders(Optional.of(incident.getDisasterId()), Optional.empty(), Optional.empty());
        // Find nearest shelter
        ShelterDTO nearestShelter = LocationHelper.findNearestShelter(incident, shelters);
        // Find nearest responder that matches Incident needs - medical kit & priority
        ResponderDTO responder = LocationHelper.findNearestResponder(incident, responders);
        incident.setAssignmentAttempts(incident.getAssignmentAttempts()+1);
        if (responder == null) {
            //Unassigned, send message to invoke unassignedIncident()
            this.unassignedIncident(incident);
        } else {
            // assign responder to incident
            // create mission
            missionPublisher.createMission(this.createMission(responder, nearestShelter, incident));
        }        
    }

    private void unassignedIncident(IncidentDTO incident) {
        // increment priority +1
        // for every victim that needs medical, increment an additional +1
        Integer priority = incident.getPriority()+1;
        for (VictimDTO victim : incident.getVictims()) {
            if (victim.isMedicalNeeded()) {
                priority++;
            }
        }
        incident.setPriority(priority);
        // Send Incident Update
        incidentPublisher.updateIncidentPriority(incident);
    }

    public void missionRejected(MissionDTO mission) {
        // Attempt to reassign incident
        IncidentDTO incident = incidentApi.incidentById(mission.getIncidentId());
        this.assignIncident(incident);
    }

    public void missionFailed(MissionDTO mission) {
        // Attempt to reassign incident
        IncidentDTO incident = incidentApi.incidentById(mission.getIncidentId());
        this.assignIncident(incident);
    }

    public void responderAvailable(ResponderDTO responder) {
        // Get unassigned incidents by priority
        List<IncidentDTO> incidents = incidentApi.incidents();
        // match responder, i.e. medical kit & Priority
        if (responder.getMedicalKit()) {
            List<IncidentDTO> needMedical = new ArrayList<IncidentDTO>();
            for (IncidentDTO incidentDTO : incidents) {
                for (VictimDTO victim : incidentDTO.getVictims()) {
                    if (victim.isMedicalNeeded()) {
                        needMedical.add(incidentDTO);
                        break;
                    }
                }
            }
            if (!needMedical.isEmpty()) {
                incidents = needMedical;
            }
        }
        Map<Integer, IncidentDTO> priorityMap = new TreeMap<Integer, IncidentDTO>();
        for (IncidentDTO incidentDTO : incidents) {
            priorityMap.put(incidentDTO.getPriority(), incidentDTO);
        }
        IncidentDTO incident = priorityMap.get(Collections.max(priorityMap.keySet()));
        // Find nearest shelter
        List<ShelterDTO> shelters = mapper.sheltersToDtos(Disaster.<Disaster>findById(incident.getDisasterId()).getShelters());
        ShelterDTO nearestShelter = LocationHelper.findNearestShelter(incident, shelters);
        // assign responder to incident
        // create mission
        missionPublisher.createMission(this.createMission(responder, nearestShelter, incident));
    }

    public void victimRescued(VictimDTO victim) {
        // Update victim status
        RescuedVictim rescuedVictim = mapper.rescuedVictimDtoToEntity(mapper.victimDtoToRescuedVictimDto(victim));
        rescuedVictim.setShelter(Shelter.findByName(victim.getShelterName()));
        RescuedVictim.persist(rescuedVictim);
    }

    private MissionDTO createMission(ResponderDTO responder, ShelterDTO shelter, IncidentDTO incident) {
        MissionDTO mission = new MissionDTO();
        LocationDTO destination = new LocationDTO();
        destination.setLatitude(shelter.getLatitude());
        destination.setLongitude(shelter.getLongitude());
        LocationDTO incidentLocation = new LocationDTO();
        incidentLocation.setLatitude(incident.getLatitude());
        incidentLocation.setLongitude(incident.getLongitude());
        LocationDTO responderStartLocation = new LocationDTO();
        responderStartLocation.setLatitude(responder.getLatitude());
        responderStartLocation.setLongitude(responder.getLongitude());
        mission.setDestination(destination);
        mission.setIncidentLocation(incidentLocation);
        mission.setResponderStartLocation(responderStartLocation);
        mission.setIncidentId(incident.getIncidentId());
        mission.setResponderId(responder.getResponderId());
        mission.setMissionId(UUID.randomUUID().toString());
        return mission;
    }
}
