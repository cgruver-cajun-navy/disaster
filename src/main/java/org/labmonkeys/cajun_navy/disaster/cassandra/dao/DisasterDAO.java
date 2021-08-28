package org.labmonkeys.cajun_navy.disaster.cassandra.dao;

import java.util.UUID;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import org.labmonkeys.cajun_navy.disaster.cassandra.model.Disaster;

@Dao
public interface DisasterDAO {
    
    @Select
    PagingIterable<Disaster> findAll();

    @Select
    Disaster findById(UUID disasterId);

    @Select(customWhereClause = "active=true")
    PagingIterable<Disaster> findActive();

    @Select(customWhereClause = "active=false")
    PagingIterable<Disaster> findInActive();

    @Insert(ifNotExists = true)
    boolean add(Disaster disaster);

    @Update(ifExists = true)
    boolean save(Disaster disaster);

}
