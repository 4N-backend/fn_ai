package com.fn.ai.hub.infrastructure.jpa;

import com.fn.ai.hub.domain.HubRoute;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouJpaRepository extends JpaRepository<HubRoute, UUID> {

}
