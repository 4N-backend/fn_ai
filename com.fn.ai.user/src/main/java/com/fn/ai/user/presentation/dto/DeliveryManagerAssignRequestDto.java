package com.fn.ai.user.presentation.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeliveryManagerAssignRequestDto(
    @NotNull(message = "출발 허브 id는 필수입니다.")
    UUID departureHubId
) {

}
