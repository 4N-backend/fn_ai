package com.fn.ai.company.application.dto.response;

import com.fn.ai.company.domain.Company;
import com.fn.ai.company.domain.CompanyType;
import java.util.UUID;

public record CompanyUpdateResponseDto(
    UUID companyId,
    String name,
    UUID HubId,
    String hubName,
    CompanyType companyType,
    String address
) {
    public static CompanyUpdateResponseDto of(Company company, String hubName) {
        return new CompanyUpdateResponseDto(company.getId(), company.getName().getValue(),
            company.getHubId(),
            hubName, company.getCompanyType(), company.getAddress().getValue()
        );
    }
}
