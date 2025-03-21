package com.fn.ai.company.infrastructure;

import com.fn.ai.company.domain.Company;
import com.fn.ai.company.domain.QCompany;
import com.fn.ai.company.domain.repository.CompanyRepository;
import com.fn.ai.company.infrastructure.jpa.CompanyJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {
    private final CompanyJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;


    @Override
    public Company save(Company company) {
        return jpaRepository.save(company);
    }

    @Override
    public Optional<Company> findById(UUID companyId) {
        return jpaRepository.findById(companyId);
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        QCompany company = QCompany.company;

        List<Company> companies = queryFactory
            .selectFrom(company)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(company.count())
            .from(company)
            .fetchOne();

        return new PageImpl<>(companies, pageable, total != null ? total : 0);
    }

    @Override
    public Page<Company> searchCompany(Pageable pageable, String keyword) {
        QCompany company = QCompany.company;

        List<Company> companies = queryFactory
            .selectFrom(company)
            .where(company.name.value.containsIgnoreCase(keyword))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(company.count())
            .from(company)
            .where(company.name.value.containsIgnoreCase(keyword))
            .fetchOne();

        return new PageImpl<>(companies, pageable, total != null ? total : 0);
    }
}
