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
@Table(name = "p_calc_hub_route_distance")
public class CalcHubRouteDistance {

    /**
     * BaseEntity 적용 X
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_hub_id")
    private UUID startHubId;

    @Column(name = "end_hub_id")
    private UUID endHubId;

    @Embedded
    private TravelTime travelTime;

    @Embedded
    private Distance distance;

    public CalcHubRouteDistance(UUID startHubId,UUID endHubId,long travelTime,long distance){
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.travelTime = new TravelTime(travelTime);
        this.distance = new Distance(distance);
    }
}
