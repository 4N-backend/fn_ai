package com.fn.ai.order.infrastructure.db;

import com.fn.ai.order.model.Order;
import com.fn.ai.order.model.OrderRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {

}
