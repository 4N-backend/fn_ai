package com.fn.ai.product.application.dto;

import java.util.UUID;

public record HubResponseDto(UUID hubId,
                             String hubName,
                             String Address,
                             Double latitude,
                             Double longitude) {

}
