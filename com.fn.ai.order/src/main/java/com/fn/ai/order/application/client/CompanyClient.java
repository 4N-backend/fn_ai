package com.fn.ai.order.application.client;

import com.fn.ai.order.application.dto.CompanyResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompanyClient {

  Optional<CompanyResponseDto> getHubByCompanyId(
      @RequestParam UUID producerId,
      @RequestParam UUID receiverId);
}
