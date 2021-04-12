package org.labmonkeys.cajun_navy.disaster.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.mapper.DisasterMapper;
import org.labmonkeys.cajun_navy.disaster.model.Disaster;
import org.labmonkeys.cajun_navy.disaster.model.InclusionZone;
import org.labmonkeys.cajun_navy.disaster.model.Shelter;

@ApplicationScoped
public class DisasterService {

    @Inject
    DisasterMapper mapper;

    @Transactional
    public List<DisasterDTO> getDisasters() {
        List<Disaster> entities = Disaster.findAll().list();
        for (Disaster disaster : entities) {
            disaster.setInclusionZones(null);
            disaster.setShelters(null);
        }
        return mapper.disastersToDtos(entities);
    }

    @Transactional
    public DisasterDTO getDisasterInfo(Long disasterId) {
        Disaster entity = Disaster.findById(disasterId);
        entity.setInclusionZones(null);
        entity.setShelters(null);
        return mapper.disasterToDto(entity);
    }

    @Transactional
    public DisasterDTO getDisasterDetails(Long disasterId) {
        return mapper.disasterToDto(Disaster.findById(disasterId));
    }

    @Transactional
    public DisasterDTO registerDisaster(DisasterDTO dto) {
        Disaster entity = mapper.disasterDtoToEntity(dto);
        Disaster.persist(entity);
        return mapper.disasterToDto(entity);
    }

    @Transactional
    public void removeDisaster(Long disasterId) {
        Disaster.deleteById(disasterId);
    }

    @Transactional
    public DisasterDTO addInclusionZone(InclusionZoneDTO dto, Long disasterId) {
        InclusionZone entity = mapper.inclusionZoneDtoToEntity(dto);
        entity.setDisaster(Disaster.findById(disasterId));
        entity.persist();
        return mapper.disasterToDto(Disaster.findById(disasterId));
    }

    @Transactional
    public void deleteInclusionZone(String zoneName) {
        InclusionZone.deleteByName(zoneName);
    }

    @Transactional
    public DisasterDTO addShelter(ShelterDTO dto, Long disasterId) {
        Shelter entity = mapper.shelterDtoToEntity(dto);
        entity.setDisaster(Disaster.findById(disasterId));
        entity.persist();
        return mapper.disasterToDto(Disaster.findById(disasterId));
    }

    @Transactional
    public DisasterDTO updateShelter(ShelterDTO dto) {
        Shelter.updateShelter(mapper.shelterDtoToEntity(dto));
        Shelter entity = Shelter.findById(dto.getShelterId());
        return mapper.disasterToDto(entity.getDisaster());
    }

    @Transactional
    public void deleteShelter(String shelterName) {
        Shelter.delete("shelterName", shelterName);
    }
}
