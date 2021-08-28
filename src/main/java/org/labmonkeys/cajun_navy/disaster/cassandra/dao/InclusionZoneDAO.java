package org.labmonkeys.cajun_navy.disaster.cassandra.dao;

import java.util.UUID;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import org.labmonkeys.cajun_navy.disaster.cassandra.model.InclusionZone;

@Dao
public interface InclusionZoneDAO {
    
    @Select
    PagingIterable<InclusionZone> findDisasterZones(UUID disasterId);

    @Select
    InclusionZone findById(UUID disasterId, UUID zoneId);

    @Insert(ifNotExists = true)
    boolean add(InclusionZone zone);

    @Update(ifExists = true)
    boolean save(InclusionZone zone);

    @Delete(ifExists = true, entityClass = InclusionZone.class)
    boolean deleteById(UUID disasterId ,UUID zoneId);
}
