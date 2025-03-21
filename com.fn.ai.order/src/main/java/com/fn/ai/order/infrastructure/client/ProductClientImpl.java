package com.fn.ai.order.infrastructure.client;

import com.fn.ai.order.application.client.ProductClient;
import com.fn.ai.order.application.dto.ProductStockRequestDto;
import com.fn.ai.order.infrastructure.ProductFeignClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {

  private final ProductFeignClient productFeignClient;

  @Override
  public Boolean reduceStockByOrderItems(List<ProductStockRequestDto> requestDto) {
    return productFeignClient.reduceStockByOrderItems(requestDto);
  }

  @Override
  public Boolean increaseStockByOrderItems(List<ProductStockRequestDto> requestDto) {
    return productFeignClient.increaseStockByOrderItems(requestDto);
  }
}
