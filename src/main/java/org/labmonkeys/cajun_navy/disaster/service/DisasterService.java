package org.labmonkeys.cajun_navy.disaster.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;

@ApplicationScoped
public class DisasterService {
    @Transactional
    public List<DisasterDTO> getDisasters() {
        return null;
    }

    @Transactional
    public DisasterDTO getDisasterInfo(String disasterId) {
        return null;
    }

    @Transactional
    public DisasterDTO getDisasterDetails(String disasterId) {
        return null;
    }

    @Transactional
    public DisasterDTO createDisaster() {
        return null;
    }

    @Transactional
    public void deleteDisaster() {

    }

    @Transactional
    public DisasterDTO addIncludsionZone() {
        return null;
    }

    @Transactional
    public DisasterDTO deleteInclusionZone() {
        return null;
    }

    @Transactional
    public DisasterDTO addShelter() {
        return null;
    }

    @Transactional
    public DisasterDTO updateShelter() {
        return null;
    }

    @Transactional
    public DisasterDTO deleteShelter() {
        return null;
    }
}
