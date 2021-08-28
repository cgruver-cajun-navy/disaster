package org.labmonkeys.cajun_navy.disaster.event;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.labmonkeys.cajun_navy.disaster.dto.DisasterDTO;
import org.labmonkeys.cajun_navy.disaster.dto.InclusionZoneDTO;
import org.labmonkeys.cajun_navy.disaster.dto.RescuedVictimDTO;
import org.labmonkeys.cajun_navy.disaster.dto.ShelterDTO;
import org.labmonkeys.cajun_navy.disaster.service.DisasterService;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.vertx.mutiny.core.eventbus.Message;

@ApplicationScoped
public class EventBusSubscriber {

    @Inject
    DisasterService service;

    @ConsumeEvent("get-disasters")
    @Blocking
    public List<DisasterDTO> getDisasters(Message<Object> msg) {
        return service.getDisasters();
    }

    @ConsumeEvent("get-disaster-info")
    @Blocking
    public DisasterDTO getDisasterInfo(UUID disasterId) {
        return service.getDisasterInfo(disasterId);
    }

    @ConsumeEvent("get-disaster-detail")
    @Blocking
    public DisasterDTO getDisasterDetails(UUID disasterId) {
        return service.getDisasterDetails(disasterId);
    }

    @ConsumeEvent("register-disaster")
    @Blocking
    public DisasterDTO registerDisaster(DisasterDTO dto) {
        return service.registerDisaster(dto);
    }

    @ConsumeEvent("remove-disaster")
    @Blocking
    public void removeDisaster(UUID disasterId) {
        service.removeDisaster(disasterId);
    }

    @ConsumeEvent("add-inclusion-zone")
    @Blocking
    public DisasterDTO addInclusionZone(ImmutablePair<InclusionZoneDTO, UUID> pair) {
        return service.addInclusionZone(pair.getLeft(), pair.getRight());
    }

    @ConsumeEvent("remove-inclusion-zone")
    @Blocking
    public void deleteInclusionZone(ImmutablePair<UUID, UUID> pair) {
        service.deleteInclusionZone(pair.getLeft(), pair.getRight());
    }

    @ConsumeEvent("add-shelter")
    @Blocking
    public DisasterDTO addShelter(ImmutablePair<ShelterDTO, UUID> pair) {
        return service.addShelter(pair.getLeft(), pair.getRight());
    }

    @ConsumeEvent("update-shelter")
    @Blocking
    public DisasterDTO updateShelter(ImmutablePair<ShelterDTO, UUID> pair) {
        return service.updateShelter(pair.getLeft(), pair.getRight());
    }

    @ConsumeEvent("remove-shelter")
    @Blocking
    public void deleteShelter(ImmutablePair<UUID, UUID> pair) {
        service.deleteShelter(pair.getLeft(), pair.getRight());;
    }

    @ConsumeEvent("rescue-victim")
    @Blocking
    public RescuedVictimDTO addRescuedVictim(Triple<RescuedVictimDTO, UUID, UUID> triple) {
        return service.addRescuedVictim(triple.getLeft(), triple.getMiddle(), triple.getRight());
    }

}
