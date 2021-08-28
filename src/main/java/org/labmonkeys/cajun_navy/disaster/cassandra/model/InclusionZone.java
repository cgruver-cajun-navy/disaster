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
public class InclusionZone {

    @PartitionKey
    private UUID disasterId;

    @ClusteringColumn
    private UUID zoneId;

    private String jsonZonePoints;
}
