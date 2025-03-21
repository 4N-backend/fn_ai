package com.fn.ai.company.application.dto.response;

import com.fn.ai.company.domain.Company;
import com.fn.ai.company.domain.CompanyType;
import java.util.UUID;

public record CompanyCreateResponseDto(
    UUID companyId,
    String name,
    UUID HubId,
    String hubName,
    CompanyType companyType,
    String address
) {

    public static CompanyCreateResponseDto of(Company company, String hubName) {
        return new CompanyCreateResponseDto(company.getId(), company.getName().getValue(),
            company.getHubId(),
            hubName, company.getCompanyType(), company.getAddress().getValue()
        );
    }

}
