package com.fn.ai.delivery.presentation.dto;

import com.fn.ai.delivery.model.type.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public record DeliveryUpdateRequestDto(
    @NotNull(message = "배송 상태는 필수입니다.")
    DeliveryStatus status
) {

}
