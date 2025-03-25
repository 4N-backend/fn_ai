package com.fn.ai.order.infrastructure.client;

import com.fn.ai.order.application.dto.ProductStockRequestDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("product-service")
public interface ProductFeignClient {

  @PostMapping("/api/products/reduce")
  Boolean reduceStockByOrderItems(@RequestBody List<ProductStockRequestDto> requestDto);

  @PostMapping("/api/products/increase")
  Boolean increaseStockByOrderItems(@RequestBody List<ProductStockRequestDto> requestDto);
}
