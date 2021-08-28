package org.labmonkeys.cajun_navy.disaster.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.labmonkeys.cajun_navy.disaster.cassandra.model.Disaster;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.InclusionZone;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.RescuedVictim;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.Shelter;
import org.labmonkeys.cajun_navy.disaster.cassandra.model.ZonePoint;
import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.RescuedVictimDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ZonePointDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface DisasterMapper {
    
    Disaster disasterDtoToEntity(DisasterDTO dto);

    @Mapping(target = "inclusionZones", ignore = true)
    @Mapping(target = "shelters", ignore = true)
    DisasterDTO disasterToDto(Disaster entity);
    
    @Mapping(target = "jsonZonePoints", ignore = true)
    InclusionZone inclusionZoneDtoToEntity(InclusionZoneDTO dto) throws JsonProcessingException;

    @AfterMapping
    default void inclusionZoneDtoToEntityCustom(InclusionZoneDTO dto, @MappingTarget InclusionZone entity) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        entity.setJsonZonePoints(jsonMapper.writeValueAsString(dto.getZonePoints()));
    }

    @Mapping(target = "zonePoints", ignore = true)
    InclusionZoneDTO inclusionZoneToDto(InclusionZone entity) throws JsonProcessingException;

    @AfterMapping
    default void inclusionZoneToDtoCustom(InclusionZone entity, @MappingTarget InclusionZoneDTO dto) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        List<ZonePoint> zonePoints = jsonMapper.readValue(entity.getJsonZonePoints(), new TypeReference<List<ZonePoint>>(){});
        List<ZonePointDTO> dtos = new ArrayList<ZonePointDTO>();
        for (ZonePoint zonePoint : zonePoints) {
            ZonePointDTO zonePointDto = new ZonePointDTO();
            zonePointDto.setLatitude(zonePoint.getLatitude());
            zonePointDto.setLongitude(zonePoint.getLongitude());
        }
        dto.setZonePoints(dtos);
    }

    Shelter shelterDtoToEntity(ShelterDTO dto);

    @Mapping(target = "rescuedVictims", ignore = true)
    ShelterDTO shelterToDto(Shelter entity);

    RescuedVictim rescuedVictimDtoToEntity(RescuedVictimDTO dto);
    RescuedVictimDTO rescuedVictimToDto(RescuedVictim entity);

    List<Disaster> disasterDtosToEntities(List<DisasterDTO> dtos);
    List<DisasterDTO> disastersToDtos(List<Disaster> entities);
    List<InclusionZone> inclusionZoneDtosToEntities(List<InclusionZoneDTO> dtos);
    List<InclusionZoneDTO> inclusionZonesToDtos(List<InclusionZone> entities);
    List<ZonePoint> zonePointDtosToEnities(List<ZonePointDTO> dtos);
    List<ZonePointDTO> zonePointsToDtos(List<ZonePoint> entites);
    List<Shelter> shelterDtosToEntites(List<ShelterDTO> dtos);
    List<ShelterDTO> sheltersToDtos(List<Shelter> entites);
    List<RescuedVictim> rescuedVictimDtosToEntities(List<RescuedVictimDTO> dtos);
    List<RescuedVictimDTO> rescuedVictimsToDtos(List<RescuedVictim> entities);
}
