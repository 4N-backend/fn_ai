package com.fn.ai.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeliveryCreateRequestDto(
    @NotNull(message = "주문 Id는 필수입니다.")
    UUID orderId,

    @NotNull(message = "출발지 허브 Id는 필수입니다.")
    UUID departureHubId,

    @NotNull(message = "목적지 허브 Id는 필수입니다.")
    UUID arrivalHubId,

    @NotBlank(message = "배송 목적지는 필수입니다.")
    String targetAddress
) {

}
