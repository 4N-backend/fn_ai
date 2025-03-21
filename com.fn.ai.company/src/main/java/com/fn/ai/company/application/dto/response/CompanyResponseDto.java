package com.fn.ai.company.application.dto.response;

import com.fn.ai.company.domain.Company;
import com.fn.ai.company.domain.CompanyType;
import java.util.UUID;

public record CompanyResponseDto(
    UUID companyId,
    String name,
    UUID HubId,
    CompanyType companyType,
    String address
) {

    public static CompanyResponseDto of(Company saved) {
        return new CompanyResponseDto(saved.getId(), saved.getName().getValue(),
            saved.getHubId(),saved.getCompanyType(), saved.getAddress().getValue());
    }
}
