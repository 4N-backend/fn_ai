package com.fn.ai.product.application.client;

import com.fn.ai.product.application.dto.HubResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;

public interface HubClient {

  Optional<HubResponseDto> getHubById(@PathVariable UUID hubId);
}
