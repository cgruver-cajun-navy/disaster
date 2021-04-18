package org.labmonkeys.cajun_navy.disaster.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.labmonkeys.cajun_navy.disaster.colaborators.incident.IncidentDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.incident.VictimDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.responder.ResponderDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;

public class LocationHelper {

    public static enum UNIT {
        MILES, KILOMETERS, NAUTICAL_MILES
    };

    public static Double distance(Double lat1, Double lon1, Double lat2, Double lon2, UNIT unit) {

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0D;
        } else {
            Double unitMultiplier = 0D;

            switch (unit) {
            case MILES:
                unitMultiplier = 1.1515;
                break;
            case KILOMETERS:
                unitMultiplier = 1.8531;
                break;
            case NAUTICAL_MILES:
                unitMultiplier = 0.99996;
                break;
            }
            Double theta = lon1 - lon2;
            return Math.toDegrees(Math.acos(
                    Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                            * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta))))
                    * 60 * unitMultiplier;
        }
    }

    public static IncidentDTO findNearestIncident(ResponderDTO responder, List<IncidentDTO> incidents) {
        Map<Double,IncidentDTO> distanceMap = new TreeMap<Double, IncidentDTO>();
        for (IncidentDTO incident : incidents) {
            distanceMap.put(distance(responder.getLatitude().doubleValue(), responder.getLongitude().doubleValue(), incident.getLatitude().doubleValue(), incident.getLongitude().doubleValue(), UNIT.MILES), incident);
        }
        return distanceMap.get(Collections.min(distanceMap.keySet()));
    }

    public static ShelterDTO findNearestShelter(IncidentDTO incident, List<ShelterDTO> shelters) {
        Map<Double,ShelterDTO> distanceMap = new TreeMap<Double, ShelterDTO>();
        for (ShelterDTO shelter : shelters) {
            if (incident.getVictims().size() <= shelter.getCapacity()) {
                distanceMap.put(distance(incident.getLatitude().doubleValue(), incident.getLongitude().doubleValue(), shelter.getLatitude().doubleValue(), shelter.getLongitude().doubleValue(), UNIT.MILES), shelter);
            }
        }
        //distanceMap.keySet().stream().mapToDouble(d -> d).min().getAsDouble();
        return distanceMap.get(Collections.min(distanceMap.keySet()));
    }

    public static ResponderDTO findNearestResponder(IncidentDTO incident, List<ResponderDTO> responders) {

        for (VictimDTO victim : incident.getVictims()) {
            if (victim.isMedicalNeeded()) {
                //Filter out Responders with no medical kit
                for (ResponderDTO responder : responders) {
                    if (!responder.getMedicalKit()) {
                        responders.remove(responder);
                    }
                }
                break;
            }
        }
        if (responders.size() == 0) {
            return null;
        }
        Map<Double,ResponderDTO> distanceMap1 = new TreeMap<Double, ResponderDTO>();
        Map<Double,ResponderDTO> distanceMap2 = new TreeMap<Double, ResponderDTO>();
        for (ResponderDTO responder : responders) {
            if (incident.getVictims().size() <= responder.getBoatCapacity()) {
                distanceMap1.put(distance(incident.getLatitude().doubleValue(), incident.getLongitude().doubleValue(), responder.getLatitude().doubleValue(), responder.getLongitude().doubleValue(), UNIT.MILES), responder);
            }
            distanceMap2.put(distance(incident.getLatitude().doubleValue(), incident.getLongitude().doubleValue(), responder.getLatitude().doubleValue(), responder.getLongitude().doubleValue(), UNIT.MILES), responder);
        }
        if (distanceMap1.size() == 0) {
            return distanceMap2.get(Collections.min(distanceMap1.keySet()));
        } else {
            return distanceMap1.get(Collections.min(distanceMap1.keySet()));
        }
    }
}
