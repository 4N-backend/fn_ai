package com.fn.ai.order.infrastructure.db;

import com.fn.ai.order.domain.model.Order;
import com.fn.ai.order.domain.repository.OrderRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID>,
    JpaOrderRepositoryCustom {

}
