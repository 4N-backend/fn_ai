package com.fn.ai.product.infrastructure.service;

import com.fn.ai.product.application.client.HubClient;
import com.fn.ai.product.application.dto.HubResponseDto;
import com.fn.ai.product.infrastructure.HubFeignClient;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {

  private final HubFeignClient hubFeignClient;

  @Override
  public Optional<HubResponseDto> getHubById(UUID hubId) {
    return hubFeignClient.getHubById(hubId);
  }

}

