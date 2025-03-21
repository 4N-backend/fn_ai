package com.fn.ai.delivery.infrastructure.db;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.repository.DeliveryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaDeliveryRepository extends DeliveryRepository, JpaRepository<Delivery, UUID> {

  @Query("""
        SELECT d FROM Delivery d 
        LEFT JOIN FETCH d.deliveryRoutes
        WHERE d.id = :id
        AND d.deletedAt IS null 
      """)
  Optional<Delivery> findByIdWithRoutes(UUID id);

  @Query("""
        SELECT d FROM Delivery d
        LEFT JOIN FETCH d.deliveryRoutes
        WHERE d.deletedAt IS null
      """)
  List<Delivery> findAllWithRoutes();

}
