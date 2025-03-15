package com.fn.ai.auth.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fn.ai.auth.presentation.dto.request.SignInRequestDto;
import com.fn.ai.auth.security.UserDetailsImpl;
import com.fn.ai.auth.security.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    setFilterProcessesUrl("/api/auth/signin");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      SignInRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          SignInRequestDto.class);
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.username(),
              requestDto.password(),
              null
          )
      );
    } catch (IOException e) {
      log.error("Failed to read login request: {}", e.getMessage());
      throw new RuntimeException(e.getMessage());
    }

  }

  @Override
  public void setFilterProcessesUrl(String filterProcessesUrl) {
    super.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher(filterProcessesUrl, "POST"));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain, Authentication authResult) {
    UUID userId = ((UserDetailsImpl) authResult.getPrincipal()).getUserId();
    String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
    UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getRole();

    String token = jwtUtil.createToken(userId, username, role);
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) {
    log.warn("Authentication failed for user: {}", request.getParameter("username"));
    response.setStatus(401);
  }

}