package com.fn.ai.hub.domain;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.hub.domain.vo.Distance;
import com.fn.ai.hub.domain.vo.TravelTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_hub_route")
public class HubRoute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_route_id")
    private UUID id;

    @Embedded
    private TravelTime travelTime;

    @Embedded
    private Distance distance;

    @Column(name = "depature_hub_id")
    private UUID departureHubId;

    @Column(name = "departure_hub_name")
    private String departureHubName;

    @Column(name = "arrival_hub_id")
    private UUID arrivalHubId;

    @Column(name = "arrival_hub_name")
    private String arrivalHubName;

    public HubRoute(long travelTime, long distance, UUID departureHubId, String departureHubName,
        UUID arrivalHubId, String arrivalHubName) {

        this.travelTime = new TravelTime(travelTime);
        this.distance = new Distance(distance);
        this.departureHubId = departureHubId;
        this.departureHubName = departureHubName;
        this.arrivalHubId = arrivalHubId;
        this.arrivalHubName = arrivalHubName;
    }

    public void update(long travelTime, long distance, UUID departureHubId, String departureHubName,
        UUID arrivalHubId, String arrivalHubName) {

        this.travelTime = new TravelTime(travelTime);
        this.distance = new Distance(distance);
        this.departureHubId = departureHubId;
        this.departureHubName = departureHubName;
        this.arrivalHubId = arrivalHubId;
        this.arrivalHubName = arrivalHubName;
    }
}
