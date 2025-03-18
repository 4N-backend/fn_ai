package com.fn.ai.order.application.service.client;

import com.fn.ai.order.application.service.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.service.dto.DeliveryCreateResponseDto;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeliveryClient {

  Optional<DeliveryCreateResponseDto> createDelivery(
      @RequestBody DeliveryCreateRequestDto requestDto);
}
