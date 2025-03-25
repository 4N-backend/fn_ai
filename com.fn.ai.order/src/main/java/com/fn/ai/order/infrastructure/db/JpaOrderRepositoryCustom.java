package com.fn.ai.order.infrastructure.db;

import com.fn.ai.order.presentation.dto.OrderSearchRequestDto;
import com.fn.ai.order.presentation.dto.OrderSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaOrderRepositoryCustom {

  Page<OrderSearchResponseDto> searchOrder(OrderSearchRequestDto requestDto, Pageable pageable);
}
