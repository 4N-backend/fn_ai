package com.fn.ai.company.application;

import com.fn.ai.company.application.dto.request.CompanyCreateRequestDto;
import com.fn.ai.company.application.dto.request.CompanyUpdateRequestDto;
import com.fn.ai.company.application.dto.response.CompanyCreateResponseDto;
import com.fn.ai.company.application.dto.response.CompanyGetHubResponseDto;
import com.fn.ai.company.application.dto.response.CompanyResponseDto;
import com.fn.ai.company.application.dto.response.CompanyUpdateResponseDto;
import com.fn.ai.company.application.dto.response.HubInfoResponseDto;
import com.fn.ai.company.application.dto.response.UserInfoResponseDto;
import com.fn.ai.company.domain.Company;
import com.fn.ai.company.domain.CompanyType;
import com.fn.ai.company.domain.repository.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final HubClient hubClient;
    private final UserClient userClient;
    private final CompanyRepository companyRepository;

    @Override
    public CompanyCreateResponseDto createCompany(CompanyCreateRequestDto requestDto) {
        UUID userId = requestDto.userId();

        UserInfoResponseDto userInfo = userClient.getUserById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다."));

        UUID hubId = requestDto.hubId();

        HubInfoResponseDto hubInfo = hubClient.getHub(hubId)
            .orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다."));

        String name = requestDto.name();
        CompanyType type = requestDto.companyType();
        String address = requestDto.address();

        Company company = new Company(userId, name, hubId, type, address);

        Company saved = companyRepository.save(company);

        return CompanyCreateResponseDto.of(saved, hubInfo.name());
    }

    @Override
    @Transactional
    public CompanyUpdateResponseDto updateCompany(UUID company_id, CompanyUpdateRequestDto requestDto) {

        Company company = companyRepository.findById(company_id)
            .orElseThrow(() -> new IllegalArgumentException("업체가 존재하지 않습니다."));

        userClient.getUserById(requestDto.userId())
            .orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다."));

        UUID hubId = requestDto.hubId();

        HubInfoResponseDto hubInfo = hubClient.getHub(hubId)
            .orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다."));

        company.update(requestDto);

        Company saved = companyRepository.save(company);

        return CompanyUpdateResponseDto.of(saved, hubInfo.name());
    }

    @Override
    public CompanyResponseDto deleteCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new IllegalArgumentException("업체가 존재하지 않습니다."));

        company.delete();

        return CompanyResponseDto.of(company);
    }

    @Override
    public CompanyResponseDto getCompanyById(UUID companyId) {

        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new IllegalArgumentException("업체가 존재하지 않습니다."));

        return CompanyResponseDto.of(company);
    }

    @Override
    public Page<CompanyResponseDto> getAllCompany(int page, int size, String sortBy,
        boolean isAsc) {

        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Company> companyPage = companyRepository.findAll(pageable);

        return companyPage.map(CompanyResponseDto::of);
    }

    @Override
    public Page<CompanyResponseDto> searchCompany(int page, int size, String sortBy, boolean isAsc,
        String keyword) {
        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Company> companyPage = companyRepository.searchCompany(pageable, keyword);

        return companyPage.map(CompanyResponseDto::of);
    }

    @Override
    public CompanyGetHubResponseDto getHubIdOfCompany(UUID companyId) {

        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new IllegalArgumentException("업체가 존재하지 않습니다."));

        HubInfoResponseDto hubInfo = hubClient.getHub(company.getHubId())
            .orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다."));

        return CompanyGetHubResponseDto.of(hubInfo);
    }

}
