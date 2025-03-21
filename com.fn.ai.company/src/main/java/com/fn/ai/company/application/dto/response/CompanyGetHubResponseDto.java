package com.fn.ai.company.application.dto.response;

import java.util.UUID;

public record CompanyGetHubResponseDto (
    UUID produceHubId,
    String produceHubAddress,
    UUID receiveHubId,
    String receiveHubAddress
){

    public static CompanyGetHubResponseDto of(HubInfoResponseDto startHubDto,HubInfoResponseDto endHubDto) {
        return new CompanyGetHubResponseDto(startHubDto.hub_id(), startHubDto.address(),
            endHubDto.hub_id(),
            endHubDto.address());
    }
}
