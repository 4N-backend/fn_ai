package com.fn.ai.order.application.client;

import com.fn.ai.order.application.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.dto.DeliveryCreateResponseDto;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeliveryClient {

  Optional<DeliveryCreateResponseDto> createDelivery(
      @RequestBody DeliveryCreateRequestDto requestDto);
}
