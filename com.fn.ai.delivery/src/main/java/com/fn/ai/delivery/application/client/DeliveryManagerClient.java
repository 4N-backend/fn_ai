package com.fn.ai.delivery.application.client;

import com.fn.ai.delivery.application.client.dto.DeliveryManagerNextSequenceResponseDto;

public interface DeliveryManagerClient {

  DeliveryManagerNextSequenceResponseDto getNextSequenceDeliveryManager(long lastSequence);

}
