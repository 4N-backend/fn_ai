package com.fn.ai.order.infrastructure.client;

import com.fn.ai.order.application.client.ProductClient;
import com.fn.ai.order.infrastructure.ProductFeignClient;
import com.fn.ai.order.presentation.dto.OrderItemRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {

  private final ProductFeignClient productFeignClient;

  @Override
  public Boolean reduceStockByOrderItems(List<OrderItemRequestDto> requestDto) {
    return productFeignClient.reduceStockByOrderItems(requestDto);
  }
}
