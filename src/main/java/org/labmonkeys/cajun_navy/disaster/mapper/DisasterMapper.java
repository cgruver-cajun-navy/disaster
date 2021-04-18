package org.labmonkeys.cajun_navy.disaster.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.labmonkeys.cajun_navy.disaster.colaborators.incident.VictimDTO;
import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.RescuedVictimDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ZonePointDTO;
import org.labmonkeys.cajun_navy.disaster.model.Disaster;
import org.labmonkeys.cajun_navy.disaster.model.InclusionZone;
import org.labmonkeys.cajun_navy.disaster.model.RescuedVictim;
import org.labmonkeys.cajun_navy.disaster.model.Shelter;
import org.labmonkeys.cajun_navy.disaster.model.ZonePoint;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface DisasterMapper {

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

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "zoom", ignore = true)
    Disaster disasterDtoToEntity(DisasterDTO dto);

    @AfterMapping
    default void disasterDtoToEntityCustom(DisasterDTO dto, @MappingTarget Disaster entity) {
        entity.setLatitude(dto.getLatitude().setScale(5, RoundingMode.HALF_UP).toString());
        entity.setLongitude(dto.getLongitude().setScale(5, RoundingMode.HALF_UP).toString());
        entity.setZoom(dto.getZoom().setScale(5, RoundingMode.HALF_UP).toString());
        for (InclusionZone zone : entity.getInclusionZones()) {
            zone.setDisaster(entity);            
        }
        for (Shelter shelter : entity.getShelters()) {
            shelter.setDisaster(entity);
        }
    }

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "zoom", ignore = true)
    DisasterDTO disasterToDto(Disaster entity);

    @AfterMapping
    default void disasterToDtoCustom(Disaster entity, @MappingTarget DisasterDTO dto) {
        dto.setLatitude(new BigDecimal(entity.getLatitude()));
        dto.setLongitude(new BigDecimal(entity.getLongitude()));
        dto.setZoom(new BigDecimal(entity.getZoom()));
    }

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    ZonePoint zonePointDtoToEntity(ZonePointDTO dto);

    @AfterMapping
    default void zonePointDtoToEntityCustom(ZonePointDTO dto, @MappingTarget ZonePoint entity) {
        entity.setLatitude(dto.getLatitude().setScale(5, RoundingMode.HALF_UP).toString());
        entity.setLongitude(dto.getLongitude().setScale(5, RoundingMode.HALF_UP).toString());
    }

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    ZonePointDTO zonePointToDto(ZonePoint entity);

    @AfterMapping
    default void zonePointToDtoCustom(ZonePoint entity, @MappingTarget ZonePointDTO dto) {
        dto.setLatitude(new BigDecimal(entity.getLatitude()));
        dto.setLongitude(new BigDecimal(entity.getLongitude()));
    }
    
    InclusionZone inclusionZoneDtoToEntity(InclusionZoneDTO dto);

    @AfterMapping
    default void inclusionZoneDtoToEntityCustom(InclusionZoneDTO dto, @MappingTarget InclusionZone entity) {
        for (ZonePoint point : entity.getZonePoints()) {
            point.setInclusionZone(entity);
        }
    }

    InclusionZoneDTO inclusionZoneToDto(InclusionZone entity);

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    Shelter shelterDtoToEntity(ShelterDTO dto);

    @AfterMapping
    default void shelterDtoToEntityCustom(ShelterDTO dto, @MappingTarget Shelter entity) {
        entity.setLatitude(dto.getLatitude().setScale(5, RoundingMode.HALF_UP).toString());
        entity.setLongitude(dto.getLongitude().setScale(5, RoundingMode.HALF_UP).toString());
    }

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    ShelterDTO shelterToDto(Shelter entity);

    @AfterMapping
    default void shelterToDto(Shelter entity, @MappingTarget ShelterDTO dto) {
        dto.setLatitude(new BigDecimal(entity.getLatitude()));
        dto.setLongitude(new BigDecimal(entity.getLongitude()));
    }

    @Mapping(target = "id", ignore = true)
    RescuedVictim rescuedVictimDtoToEntity(RescuedVictimDTO dto);

    RescuedVictimDTO rescuedVictimToDto(RescuedVictim entity);

    @Mapping(source = "victimId", target = "victimId")
    @Mapping(source = "medicalNeeded", target = "medicalNeeded")
    @Mapping(source = "victimName", target = "victimName")
    @Mapping(source = "victimPhoneNumber", target = "victimPhoneNumber")
    RescuedVictimDTO victimDtoToRescuedVictimDto(VictimDTO dto);
}
