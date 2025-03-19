package com.fn.ai.order.infrastructure.client;

import com.fn.ai.order.application.client.CompanyClient;
import com.fn.ai.order.application.dto.CompanyResponseDto;
import com.fn.ai.order.infrastructure.CompanyFeignClient;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {

  private final CompanyFeignClient companyFeignClient;

  @Override
  public Optional<CompanyResponseDto> getHubByCompanyId(UUID supplierId, UUID receiverId) {
    return companyFeignClient.getHubByCompanyId(supplierId, receiverId);
  }
}
