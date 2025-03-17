package com.fn.ai.hub.application.dto.request;

public record HubCreateRequestDto(
    String name,
    String address,
    Double latitude,
    Double longitude
){

}
