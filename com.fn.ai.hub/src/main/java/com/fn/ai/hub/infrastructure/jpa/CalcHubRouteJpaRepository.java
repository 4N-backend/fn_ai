package com.fn.ai.hub.infrastructure.jpa;

import com.fn.ai.hub.domain.CalcHubRouteDistance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalcHubRouteJpaRepository extends JpaRepository<CalcHubRouteDistance, Long> {


}
