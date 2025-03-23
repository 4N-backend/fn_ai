package com.fn.ai.delivery.application.client;

import com.fn.ai.delivery.application.client.dto.HubRouteRequestDto;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import java.util.Queue;

public interface HubClient {

  Queue<HubRouteResponseDto> getHubRoute(HubRouteRequestDto requestDto);

}
