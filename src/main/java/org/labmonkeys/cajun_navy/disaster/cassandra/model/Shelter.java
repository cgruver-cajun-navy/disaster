package org.labmonkeys.cajun_navy.disaster.cassandra.model;

import java.math.BigDecimal;
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
public class Shelter {

    @PartitionKey
    private UUID disasterId;

    @ClusteringColumn(0)
    private UUID shelterId;

    private String shelterName;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private long capacity;

}
