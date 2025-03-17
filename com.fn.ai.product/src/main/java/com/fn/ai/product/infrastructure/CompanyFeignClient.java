package com.fn.ai.product.infrastructure;

import com.fn.ai.product.application.dto.CompanyResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("company-service")
public interface CompanyFeignClient {

  @GetMapping("/api/companies/{companyId}")
  Optional<CompanyResponseDto> getCompanyById(@PathVariable UUID companyId);

}
