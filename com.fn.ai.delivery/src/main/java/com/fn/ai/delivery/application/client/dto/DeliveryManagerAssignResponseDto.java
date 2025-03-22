package com.fn.ai.delivery.application.client.dto;

import java.util.UUID;

public record DeliveryManagerAssignResponseDto(
    UUID deliveryManagerId,
    String slackId
) {

}
