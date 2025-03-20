package com.fn.ai.order.model;

import com.fn.ai.order.presentation.OrderSearchRequestDto;
import com.fn.ai.order.presentation.OrderSearchResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(UUID orderId);

  Page<OrderSearchResponseDto> searchOrder(OrderSearchRequestDto orderSearchRequestDto,
      Pageable pageable);
}
