package com.fn.ai.hub.application.dto.response;

import com.fn.ai.hub.domain.Hub;
import java.util.UUID;

public record HubResponseDto(
    UUID hub_id,
    String name,
    String address,
    Double latitude,
    Double longitude
) {

    public static HubResponseDto of(Hub hub) {
        return new HubResponseDto(hub.getId(), hub.getName().getValue(),
            hub.getAddress().getValue(), hub.getLocation()
            .getLatitude(), hub.getLocation().getLongitude());
    }
}
