package com.fn.ai.company.application.dto.response;

import java.util.UUID;

public record CompanyGetHubResponseDto (
    UUID hubId,
    String hubAddress
){

    public static CompanyGetHubResponseDto of(HubInfoResponseDto responseDto) {
        return new CompanyGetHubResponseDto(responseDto.hub_id(), responseDto.address());
    }
}
