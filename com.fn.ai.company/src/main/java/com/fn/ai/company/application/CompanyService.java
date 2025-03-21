package com.fn.ai.company.application;

import com.fn.ai.company.application.dto.request.CompanyCreateRequestDto;
import com.fn.ai.company.application.dto.request.CompanyUpdateRequestDto;
import com.fn.ai.company.application.dto.response.CompanyCreateResponseDto;
import com.fn.ai.company.application.dto.response.CompanyGetHubResponseDto;
import com.fn.ai.company.application.dto.response.CompanyResponseDto;
import com.fn.ai.company.application.dto.response.CompanyUpdateResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CompanyService {

    CompanyCreateResponseDto createCompany(CompanyCreateRequestDto requestDto);

    CompanyUpdateResponseDto updateCompany(UUID company_id, CompanyUpdateRequestDto requestDto);

    CompanyResponseDto deleteCompany(UUID companyId);

    CompanyResponseDto getCompanyById(UUID companyId);

    Page<CompanyResponseDto> getAllCompany(int page, int size, String sortBy, boolean isAsc);

    Page<CompanyResponseDto> searchCompany(int page, int size, String sortBy, boolean isAsc, String keyword);

    CompanyGetHubResponseDto getHubIdOfCompany(UUID companyId);
}
