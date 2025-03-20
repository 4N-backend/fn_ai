package com.fn.ai.hub.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record HubCreateDeliveryRouteRequestDto(
    @NotBlank(message = "출발 허브 Id는 필수 입력값 입니다.") UUID departureHubId,
    @NotBlank(message = "도착 허브 Id는 필수 입력값 입니다.") UUID arrivalHubId

) {

}
