package org.labmonkeys.cajun_navy.disaster.cassandra.model;

import java.util.UUID;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(defaultKeyspace = "DisasterData")
@NamingStrategy(convention = NamingConvention.EXACT_CASE)
public class RescuedVictim {

    @PartitionKey
    private UUID disasterId;

    @ClusteringColumn(0)
    private UUID shelterId;

    @ClusteringColumn(1)
    private UUID victimId;

    private boolean medicalNeeded;

    private String victimName;

    private String victimPhoneNumber;

}
