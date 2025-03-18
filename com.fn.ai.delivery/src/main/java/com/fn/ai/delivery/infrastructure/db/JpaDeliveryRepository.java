package com.fn.ai.delivery.infrastructure.db;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.repository.DeliveryRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryRepository extends DeliveryRepository, JpaRepository<Delivery, UUID> {

}
