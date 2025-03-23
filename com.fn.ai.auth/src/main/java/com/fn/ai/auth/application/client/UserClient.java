package com.fn.ai.auth.application.client;

import com.fn.ai.auth.application.client.dto.UserRegisterRequestDto;
import com.fn.ai.auth.application.client.dto.UserResponseDto;
import com.fn.ai.auth.presentation.dto.response.SignUpResponseDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {

  @PostMapping("/api/users/signup")
  Optional<SignUpResponseDto> signup(@RequestBody UserRegisterRequestDto dto);

  @GetMapping("/api/users/username/{username}")
  Optional<UserResponseDto> getUserByUsername(@PathVariable String username);
}
