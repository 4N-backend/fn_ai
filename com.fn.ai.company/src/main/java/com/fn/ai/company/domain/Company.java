package com.fn.ai.company.domain;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.company.application.dto.request.CompanyUpdateRequestDto;
import com.fn.ai.company.domain.vo.Address;
import com.fn.ai.company.domain.vo.Name;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Embedded
    private Name name;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(name = "company_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Embedded
    private Address address;

    public Company(UUID userId, String name, UUID hubId, CompanyType companyType, String address) {
        this.userId = userId;
        this.name = new Name(name);
        this.hubId = hubId;
        this.companyType = companyType;
        this.address = new Address(address);
    }

    public void update(CompanyUpdateRequestDto requestDto) {
        this.userId = requestDto.userId();
        this.name = new Name(requestDto.name());
        this.hubId = requestDto.hubId();
        this.companyType = requestDto.companyType();
        this.address = new Address(requestDto.address());
    }
}
