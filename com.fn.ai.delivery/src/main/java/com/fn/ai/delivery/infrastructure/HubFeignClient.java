package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

  @GetMapping("/api/hubs/routes/b2b")
  HubRouteResponseDto getHubRoute(
      @RequestParam UUID departureHubId,
      @RequestParam UUID arrivalHubId);
}
