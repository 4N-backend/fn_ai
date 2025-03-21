package com.fn.ai.company.infrastructure.jpa;

import com.fn.ai.company.domain.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, UUID> {


}
