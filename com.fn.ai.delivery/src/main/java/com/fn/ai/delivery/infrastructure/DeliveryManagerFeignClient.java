package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.dto.DeliveryManagerAssignResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface DeliveryManagerFeignClient {

  @GetMapping("/delivery-managers/{departureHubId}/assign")
  DeliveryManagerAssignResponseDto getAssignDeliveryManager(
      @PathVariable("departureHubId") UUID departureHubId);
}
