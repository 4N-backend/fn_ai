package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.dto.DeliveryManagerNextSequenceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface DeliveryManagerFeignClient {

  @GetMapping("/api/delivery-managers/{lastSequence}/next")
  DeliveryManagerNextSequenceResponseDto getNextSequenceDeliveryManager(
      @PathVariable("lastSequence") long lastSequence);
}
