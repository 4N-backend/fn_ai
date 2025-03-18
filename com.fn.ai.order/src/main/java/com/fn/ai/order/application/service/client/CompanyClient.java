package com.fn.ai.order.application.service.client;

import com.fn.ai.order.application.service.dto.CompanyResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompanyClient {

  Optional<CompanyResponseDto> getHubByCompanyId(
      @RequestParam UUID supplierId,
      @RequestParam UUID receiverId);
}
