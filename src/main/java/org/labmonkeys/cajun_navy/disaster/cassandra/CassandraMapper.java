package org.labmonkeys.cajun_navy.disaster.cassandra;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

import org.labmonkeys.cajun_navy.disaster.cassandra.dao.DisasterDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.dao.InclusionZoneDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.dao.RescuedVictimDAO;
import org.labmonkeys.cajun_navy.disaster.cassandra.dao.ShelterDAO;

@Mapper
public interface CassandraMapper {
    @DaoFactory
    DisasterDAO disasterDAO();

    @DaoFactory
    InclusionZoneDAO inclusionZoneDAO();

    @DaoFactory
    RescuedVictimDAO rescuedVictimDAO();

    @DaoFactory
    ShelterDAO shelterDAO();
}
