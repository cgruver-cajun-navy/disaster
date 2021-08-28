package org.labmonkeys.cajun_navy.disaster.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.labmonkeys.cajun_navy.disaster.cassandra.dao.DisasterDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.dao.InclusionZoneDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.dao.RescuedVictimDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.dao.ShelterDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.Disaster;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.InclusionZone;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.RescuedVictim;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.Shelter;
import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.RescuedVictimDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.dtoMapper.DisasterMapper;

@ApplicationScoped
public class DisasterService {

    @Inject
    DisasterMapper dtoMapper;

    @Inject
    DisasterDAO disasterDao;

    @Inject
    InclusionZoneDAO inclusionZoneDao;

    @Inject
    RescuedVictimDAO victimDao;

    @Inject
    ShelterDAO shelterDao;

    public List<DisasterDTO> getDisasters() {
        List<Disaster> entities = disasterDao.findAll().all();
        return dtoMapper.disastersToDtos(entities);
    }

    public DisasterDTO getDisasterInfo(UUID disasterId) {
        Disaster entity = disasterDao.findById(disasterId);
        return dtoMapper.disasterToDto(entity);
    }

    public DisasterDTO getDisasterDetails(UUID disasterId) {
        Disaster entity = disasterDao.findById(disasterId);
        List<Shelter> shelters = shelterDao.findByDisaster(disasterId).all();
        List<InclusionZone> inclusionZones = inclusionZoneDao.findDisasterZones(disasterId).all();
        DisasterDTO dto = dtoMapper.disasterToDto(entity);
        dto.setShelters(dtoMapper.sheltersToDtos(shelters));
        dto.setInclusionZones(dtoMapper.inclusionZonesToDtos(inclusionZones));
        return dto;
    }

    public DisasterDTO registerDisaster(DisasterDTO dto) {
        dto.setDisasterId(UUID.randomUUID());
        Disaster entity = dtoMapper.disasterDtoToEntity(dto);
        disasterDao.add(entity);
        return dto;
    }

    public void removeDisaster(UUID disasterId) {
        Disaster entity = disasterDao.findById(disasterId);
        if(entity != null) {
            entity.setActive(false);
            disasterDao.save(entity);
        }
    }

    public DisasterDTO addInclusionZone(InclusionZoneDTO dto, UUID disasterId) {
        InclusionZone entity;
        try {
            entity = dtoMapper.inclusionZoneDtoToEntity(dto);
            entity.setDisasterId(disasterId);
            entity.setZoneId(UUID.randomUUID());
            inclusionZoneDao.add(entity);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.getDisasterDetails(disasterId);
    }

    public void deleteInclusionZone(UUID disasterId, UUID zoneId) {
        inclusionZoneDao.deleteById(disasterId, zoneId);
    }

    public DisasterDTO addShelter(ShelterDTO dto, UUID disasterId) {
        Shelter entity = dtoMapper.shelterDtoToEntity(dto);
        entity.setDisasterId(disasterId);
        entity.setShelterId(UUID.randomUUID());
        return this.getDisasterDetails(disasterId);
    }

    public DisasterDTO updateShelter(ShelterDTO dto, UUID disasterId) {
        shelterDao.save(dtoMapper.shelterDtoToEntity(dto));
        return this.getDisasterDetails(disasterId);
    }

    public void deleteShelter(UUID shelterId, UUID disasterId) {
        shelterDao.deleteById(disasterId, shelterId);
    }

    public RescuedVictimDTO addRescuedVictim(RescuedVictimDTO dto, UUID disasterId, UUID shelterId) {
        dto.setVictimId(UUID.randomUUID());
        RescuedVictim entity = dtoMapper.rescuedVictimDtoToEntity(dto);
        entity.setDisasterId(disasterId);
        entity.setShelterId(shelterId);
        victimDao.add(entity);
        return dto;
    }

    public void updateRescuedVictim(RescuedVictimDTO dto, UUID disasterId, UUID shelterId) {
        RescuedVictim entity = dtoMapper.rescuedVictimDtoToEntity(dto);
        entity.setDisasterId(disasterId);
        entity.setShelterId(shelterId);
        victimDao.save(entity);
    }
}
