package com.fn.ai.hub.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record HubRouteCreateRequestDto(
    @NotBlank(message = "출발 허브 ID는 필수 입력값 입니다.") UUID departureHubId,
    @NotBlank(message = "도착 허브 ID는 필수 입력값 입니다.") UUID arrivalHubId
    ) {
}
