package com.fn.ai.hub.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record HubUpdateRequestDto(
    @NotBlank(message = "이름은 필수 입력값 입니다.") String name,
    @NotBlank(message = "주소는 필수 입력값 입니다.") String address,
    @NotBlank(message = "위도는 필수 입력값 입니다.") Double latitude,
    @NotBlank(message = "경도는 필수 입력값 입니다.") Double longitude
) {

}
