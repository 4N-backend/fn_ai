package com.fn.ai.order.application.dto;

import java.util.UUID;

public record CompanyResponseDto(UUID produceHubId,
                                 String produceHubAddress,
                                 UUID receiveHubId,
                                 String receiveHubAddress) {

}
