package com.fn.ai.hub.domain;

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
public class HubRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_route_id")
    private UUID id;

    @Embedded
    private TravelTime travelTime;

    @Embedded
    private Distance distance;

    @Column(name = "depature_hub_id")
    private UUID depatureHubId;

    @Column(name = "arrival_hub_id")
    private UUID arrivalHubId;

    public HubRoute(long travelTime, long distance, UUID depatureHubId, UUID arrivalHubId) {
        this.travelTime = new TravelTime(travelTime);
        this.distance = new Distance(distance);
        this.depatureHubId = depatureHubId;
        this.arrivalHubId = arrivalHubId;
    }
}
