package com.fn.ai.company.application.dto.request;

import com.fn.ai.company.domain.CompanyType;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CompanyCreateRequestDto(
    @NotBlank(message = "유저 아이디는 필수 값입니다.") UUID userId,
    @NotBlank(message = "이름은 필수 값입니다.")String name,
    @NotBlank(message = "소속 허브 아이디는 필수 값입니다.")UUID hubId,
    @NotBlank(message = "업체 타입은 필수 값입니다.")CompanyType companyType,
    @NotBlank(message = "업체 주소는 필수 값입니다.")String address
) {
}
