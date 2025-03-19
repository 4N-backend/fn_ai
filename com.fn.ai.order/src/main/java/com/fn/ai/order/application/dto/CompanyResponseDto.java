package com.fn.ai.order.application.dto;

import java.util.UUID;

public record CompanyResponseDto(UUID supplierHubId,
                                 UUID receiverHubId,
                                 String supplierHubAddress) {

}
