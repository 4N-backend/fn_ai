package com.fn.ai.product.application.client;

import com.fn.ai.product.application.dto.CompanyResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;

public interface CompanyClient {

  Optional<CompanyResponseDto> getCompanyById(@PathVariable UUID companyId);
}
