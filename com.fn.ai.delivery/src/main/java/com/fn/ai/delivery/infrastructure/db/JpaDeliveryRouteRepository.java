package com.fn.ai.delivery.infrastructure.db;

import com.fn.ai.delivery.model.DeliveryRoute;
import com.fn.ai.delivery.model.repository.DeliveryRouteRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryRouteRepository extends DeliveryRouteRepository,
    JpaRepository<DeliveryRoute, UUID> {

}
