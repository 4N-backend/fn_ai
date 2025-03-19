package com.fn.ai.order.application.client;

import com.fn.ai.order.presentation.dto.OrderItemRequestDto;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductClient {

  Boolean reduceStockByOrderItems(
      @RequestBody List<OrderItemRequestDto> requestDto);
}
