package com.fn.ai.delivery.model.repository;

import com.fn.ai.delivery.model.Delivery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {

  Delivery save(Delivery delivery);

  Optional<Delivery> findById(UUID deliveryId);

  List<Delivery> findAll();

  Optional<Delivery> findByIdWithRoutes(UUID id);

  List<Delivery> findAllWithRoutes();

}
