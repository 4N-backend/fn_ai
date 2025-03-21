package com.fn.ai.company.application.dto.request;

import com.fn.ai.company.domain.CompanyType;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CompanyUpdateRequestDto(
    @NotBlank(message = "유저 아이디는 필수값 입니다.") UUID userId,
    @NotBlank(message = "업체명은 필수값 입니다.") String name,
    @NotBlank(message = "소속 허브는 필수값 입니다.") UUID hubId,
    @NotBlank(message = "업체 타입은 필수값 입니다.") CompanyType companyType,
    @NotBlank(message = "업체 주소는 필수값 입니다.") String address
) {

}
