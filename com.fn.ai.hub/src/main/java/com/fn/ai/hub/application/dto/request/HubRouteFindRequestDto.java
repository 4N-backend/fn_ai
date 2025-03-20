package com.fn.ai.hub.application.dto.request;

import java.util.UUID;

public record HubRouteFindRequestDto (
        UUID departureHubId,
        UUID arrivalHubId
){
}
