package com.fn.ai.product.infrastructure;

import com.fn.ai.product.application.dto.HubResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("hub-service")
public interface HubFeignClient {

  @GetMapping("/{hubId}")
  Optional<HubResponseDto> getHubById(@PathVariable UUID hubId);

}
