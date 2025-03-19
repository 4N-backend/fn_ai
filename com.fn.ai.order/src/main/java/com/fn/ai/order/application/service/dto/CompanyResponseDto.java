package com.fn.ai.order.application.service.dto;

import java.util.UUID;

public record CompanyResponseDto(UUID supplierHubId,
                                 UUID receiverHubId,
                                 String supplierHubAddress) {

}
