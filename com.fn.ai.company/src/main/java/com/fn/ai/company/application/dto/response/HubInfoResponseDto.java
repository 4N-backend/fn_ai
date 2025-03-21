package com.fn.ai.company.application.dto.response;

import java.util.UUID;

public record HubInfoResponseDto(
    UUID hub_id,
    String name,
    String address,
    Double latitude,
    Double longitude
) {

}
