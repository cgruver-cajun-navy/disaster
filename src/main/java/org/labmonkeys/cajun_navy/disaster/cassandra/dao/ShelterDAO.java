package org.labmonkeys.cajun_navy.disaster.cassandra.dao;

import java.util.UUID;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import org.labmonkeys.cajun_navy.disaster.cassandra.model.Shelter;

@Dao
public interface ShelterDAO {
    
    @Select
    PagingIterable<Shelter> findByDisaster(UUID disasterId);

    @Select
    Shelter findByShelter(UUID disasterId, UUID ShelterId);

    @Insert(ifNotExists = true)
    boolean add(Shelter shelter);

    @Update(ifExists = true)
    boolean save(Shelter shelter);

    @Delete(ifExists = true, entityClass = Shelter.class)
    boolean deleteById(UUID disasterId, UUID shelterId);
}
