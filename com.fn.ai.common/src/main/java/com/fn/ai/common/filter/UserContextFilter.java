package com.fn.ai.common.filter;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import com.fn.ai.common.context.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
public class UserContextFilter extends OncePerRequestFilter {

  private static final String USER_ID = "X-User-Id";
  private static final String USER_NAME = "X-User-Name";
  private static final String USER_ROLE = "X-User-Role";

  private static final Set<String> SWAGGER_URIS = Set.of(
      "/v3/api-docs",
      "/swagger-ui",
      "/swagger-resources",
      "/webjars",
      "/favicon.ico",
      "/swagger-ui.html"
  );

  public boolean isExcludedUri(String requestUri) {
    return SWAGGER_URIS.stream().anyMatch(requestUri::endsWith);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String requestUri = request.getRequestURI();

    String modifiedUri = requestUri.replaceFirst("/api/[^/]+", "");

    log.info("Request URI : {}", requestUri);

    // Swagger 관련 요청은 필터 적용하지 않음
    if (isExcludedUri(requestUri)) {
      RequestDispatcher dispatcher = request.getRequestDispatcher(modifiedUri);
      dispatcher.forward(request, response);
      return;
    }

    if (requestUri.startsWith("/api/users/username") ||
        requestUri.startsWith("/api/auth/signup")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      UserContext userContext = UserContext.builder()
          .username(request.getHeader(USER_NAME))
          .userId(UUID.fromString(request.getHeader(USER_ID)))
          .userRole(UserRoleEnum.valueOf(request.getHeader(USER_ROLE)))
          .build();

      UserContextHolder.setUserContext(userContext);
      filterChain.doFilter(request, response);
    } finally {
      UserContextHolder.clear(); // 요청이 끝나면 제거
    }
  }
}
