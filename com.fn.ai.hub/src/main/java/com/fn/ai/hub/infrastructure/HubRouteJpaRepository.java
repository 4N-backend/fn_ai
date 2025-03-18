package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.HubRoute;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouteJpaRepository extends JpaRepository<HubRoute, UUID> {

}
