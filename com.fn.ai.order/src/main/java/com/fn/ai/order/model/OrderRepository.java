package com.fn.ai.order.model;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(UUID orderId);
}
