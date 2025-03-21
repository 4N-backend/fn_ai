package com.fn.ai.company.application;

import com.fn.ai.company.application.dto.response.UserInfoResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/user/info/{userId}")
    Optional<UserInfoResponseDto> getUserById(@PathVariable UUID userId);

}
