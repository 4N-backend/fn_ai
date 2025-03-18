package com.fn.ai.order.infrastructure.client;

import com.fn.ai.order.application.service.client.CompanyClient;
import com.fn.ai.order.application.service.dto.CompanyResponseDto;
import com.fn.ai.order.infrastructure.CompanyFeignClient;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {

  private final CompanyFeignClient companyFeignClient;

  @Override
  public Optional<CompanyResponseDto> getHubByCompanyId(
      @PathVariable UUID supplierId,
      @PathVariable UUID receiverId) {
    return companyFeignClient.getHubByCompanyId(supplierId, receiverId);
  }
}
