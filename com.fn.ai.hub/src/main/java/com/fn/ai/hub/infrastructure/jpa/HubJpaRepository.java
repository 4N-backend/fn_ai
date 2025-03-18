package com.fn.ai.hub.infrastructure.jpa;

import com.fn.ai.hub.domain.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<Hub, UUID> {

}
