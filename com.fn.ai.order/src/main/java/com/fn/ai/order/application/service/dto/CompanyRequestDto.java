package com.fn.ai.order.application.service.dto;

import java.util.UUID;

public record CompanyRequestDto(UUID supplierId,
                                UUID receiverId) {

}
