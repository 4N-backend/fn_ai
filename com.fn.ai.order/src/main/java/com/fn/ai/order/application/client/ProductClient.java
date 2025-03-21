package com.fn.ai.order.application.client;

import com.fn.ai.order.application.dto.ProductStockRequestDto;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductClient {

  Boolean reduceStockByOrderItems(
      @RequestBody List<ProductStockRequestDto> requestDto);

  Boolean increaseStockByOrderItems(
      @RequestBody List<ProductStockRequestDto> requestDto);
}
