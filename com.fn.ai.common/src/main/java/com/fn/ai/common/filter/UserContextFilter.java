package com.fn.ai.common.filter;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import com.fn.ai.common.context.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class UserContextFilter extends OncePerRequestFilter {

  private static final String USER_ID = "X-User-Id";
  private static final String USER_NAME = "X-User-Name";
  private static final String USER_ROLE = "X-User-Role";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String requestUri = request.getRequestURI();
    String method = request.getMethod();


    if (requestUri.startsWith("/api/user") && method.equals("POST")) {
      filterChain.doFilter(request, response);
      return;
    }


    UserContext userContext = UserContext.builder()
        .username(request.getHeader(USER_NAME))
        .userId(UUID.fromString(request.getHeader(USER_ID)))
        .userRole(UserRoleEnum.valueOf(request.getHeader(USER_ROLE)))
        .build();

    UserContextHolder.setUserContext(userContext);

    try {
      filterChain.doFilter(request, response);
    } finally {
      UserContextHolder.clear(); // 요청이 끝나면 제거
    }


  }
}
