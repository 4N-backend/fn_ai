package com.fn.ai.company.application;

import com.fn.ai.company.application.dto.response.HubInfoResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="hub-service")
public interface HubClient {

    @GetMapping("/api/hubs/client/{hub_id}")
    Optional<HubInfoResponseDto> getHub(@PathVariable UUID hub_id);


}
