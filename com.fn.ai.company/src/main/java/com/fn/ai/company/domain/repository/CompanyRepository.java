package com.fn.ai.company.domain.repository;

import com.fn.ai.company.domain.Company;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findById(UUID companyId);

    Page<Company> findAll(Pageable pageable);

    Page<Company> searchCompany(Pageable pageable, String keyword);
}
