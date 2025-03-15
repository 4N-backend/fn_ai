package com.fn.ai.auth.security;

import com.fn.ai.auth.application.client.UserClient;
import com.fn.ai.auth.application.client.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserClient userClient;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserResponseDto userResponseDto = userClient.getUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return new UserDetailsImpl(UserResponseDto.of(
        userResponseDto.id(),
        userResponseDto.username(),
        userResponseDto.password(),
        userResponseDto.role()));
  }
}