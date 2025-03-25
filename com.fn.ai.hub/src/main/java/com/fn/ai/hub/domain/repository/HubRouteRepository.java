package com.fn.ai.hub.domain.repository;

import com.fn.ai.hub.domain.HubRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteRepository {
    HubRoute save(HubRoute route);

    boolean existsByDepatureHubIdAndArrivalHubId(UUID departureHubId, UUID arrivalHubId);

    List<HubRoute> findAll();

    Optional<HubRoute> findByDepatureHubIdAndArrivalHubId(UUID departureHubId, UUID  arrivalHubId);

    Page<HubRoute> findAllByPage(Pageable pageable);

    Optional<HubRoute> findHubRouteById(UUID routeId);

    Page<HubRoute> searchHubRoute(Pageable pageable, String keyword);

    Optional<HubRoute> findById(UUID routeId);
}
