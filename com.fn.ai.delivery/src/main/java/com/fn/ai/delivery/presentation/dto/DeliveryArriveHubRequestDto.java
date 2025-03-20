package com.fn.ai.delivery.presentation.dto;

import jakarta.validation.constraints.Positive;

public record DeliveryArriveHubRequestDto(
    @Positive(message = "소요 시간은 양수여야합니다.")
    Long duration,
    @Positive(message = "이동 거리는 양수여야합니다.")
    Double distance
) {

}
