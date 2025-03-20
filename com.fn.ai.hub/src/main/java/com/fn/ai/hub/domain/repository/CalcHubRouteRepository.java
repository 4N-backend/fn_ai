package com.fn.ai.hub.domain.repository;

import com.fn.ai.hub.domain.CalcHubRouteDistance;
import java.util.List;

public interface CalcHubRouteRepository {

    CalcHubRouteDistance save(CalcHubRouteDistance calculateHubDistance);

    List<CalcHubRouteDistance> findAll();
}
