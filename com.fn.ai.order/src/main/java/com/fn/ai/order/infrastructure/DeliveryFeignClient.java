package com.fn.ai.order.infrastructure;

import com.fn.ai.order.application.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.dto.DeliveryCreateResponseDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("delivery-service")
public interface DeliveryFeignClient {

  @PostMapping
  Optional<DeliveryCreateResponseDto> createDelivery(
      @RequestBody DeliveryCreateRequestDto requestDto);
}
