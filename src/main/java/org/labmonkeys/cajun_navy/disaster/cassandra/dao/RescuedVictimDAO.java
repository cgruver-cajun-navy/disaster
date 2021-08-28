package org.labmonkeys.cajun_navy.disaster.cassandra.dao;

import java.util.UUID;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import org.labmonkeys.cajun_navy.disaster.cassandra.model.RescuedVictim;

@Dao
public interface RescuedVictimDAO {
    
    @Select
    PagingIterable<RescuedVictim> findByDisaster(UUID disasterId);

    @Select
    PagingIterable<RescuedVictim> findByShelter(UUID disasterId, UUID ShelterId);

    @Select
    PagingIterable<RescuedVictim> findById(UUID disasterId, UUID ShelterId, UUID victimId);

    @Select(customWhereClause = "victimPhoneNumber=:phone")
    PagingIterable<RescuedVictim> findByPhone(String phone);

    @Select(customWhereClause = "victimName LIKE :name")
    PagingIterable<RescuedVictim> findByName(String name);

    @Select(customWhereClause = "medicalNeeded=true")
    PagingIterable<RescuedVictim> findByMedicalNeeded();

    @Insert(ifNotExists = true)
    boolean add(RescuedVictim victim);

    @Update(ifExists = true)
    boolean save(RescuedVictim victim);

    @Delete(ifExists = true, entityClass = RescuedVictim.class)
    boolean delete(UUID disasterId, UUID shelterId, UUID victimId);
}
