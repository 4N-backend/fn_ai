package com.fn.ai.delivery.application.client;

import com.fn.ai.delivery.application.client.dto.HubRouteRequestDto;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;

public interface HubClient {

  HubRouteResponseDto getHubRoute(HubRouteRequestDto requestDto);

}
