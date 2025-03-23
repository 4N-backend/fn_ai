package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.dto.HubRouteRequestDto;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import java.util.Queue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

  @GetMapping("/routes/b2b")
  Queue<HubRouteResponseDto> getHubRoute(@RequestBody HubRouteRequestDto requestDto);
}
