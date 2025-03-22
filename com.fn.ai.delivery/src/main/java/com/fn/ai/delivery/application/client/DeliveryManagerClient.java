package com.fn.ai.delivery.application.client;

import com.fn.ai.delivery.application.client.dto.DeliveryManagerAssignResponseDto;
import java.util.UUID;

public interface DeliveryManagerClient {

  DeliveryManagerAssignResponseDto getAssignedDeliveryManager(UUID departureId);

}
