package com.fn.ai.order.infrastructure;

import com.fn.ai.order.presentation.dto.OrderItemRequestDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("product-service")
public interface ProductFeignClient {

  @PostMapping("/api/products/reduce")
  Boolean reduceStockByOrderItems(@RequestBody List<OrderItemRequestDto> requestDto);
}
