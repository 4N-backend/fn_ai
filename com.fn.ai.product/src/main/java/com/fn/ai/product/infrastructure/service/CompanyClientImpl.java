package com.fn.ai.product.infrastructure.service;

import com.fn.ai.product.application.client.CompanyClient;
import com.fn.ai.product.application.dto.CompanyResponseDto;
import com.fn.ai.product.infrastructure.CompanyFeignClient;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {

  private final CompanyFeignClient companyFeignClient;


  @Override
  public Optional<CompanyResponseDto> getCompanyById(UUID companyId) {
    return companyFeignClient.getCompanyById(companyId);
  }
}
