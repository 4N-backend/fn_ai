package com.fn.ai.order.infrastructure;

import com.fn.ai.order.application.dto.CompanyResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("company-service")
public interface CompanyFeignClient {

  @GetMapping("/info")
  Optional<CompanyResponseDto> getHubByCompanyId(
      @RequestParam UUID producerId,
      @RequestParam UUID receiverId);
}
