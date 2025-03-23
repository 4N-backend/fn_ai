package com.fn.ai.common.config;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignConfig {

  private static final String USER_ID = "X-User-Id";
  private static final String USER_NAME = "X-User-Name";
  private static final String USER_ROLE = "X-User-Role";

  @Bean
  public RequestInterceptor requestInterceptor() {
    return new RequestInterceptor() {
      @Override
      public void apply(RequestTemplate template) {
        UserContext context = UserContextHolder.getContext();
        if (context != null) {
          if (context.userId() != null) {
            template.header(USER_ID, context.userId().toString());
            log.info("Feign: Current UserId = {}", context.userId().toString());
          }
          if (context.username() != null) {
            template.header(USER_NAME, context.username());
            log.info("Feign: Current Username = {}", context.username());
          }
          if (context.userRole() != null) {
            template.header(USER_ROLE, context.userRole().toString());
            log.info("Feign: Current UserRole = {}", context.userRole().toString());
          }
        }
      }
    };
  }
}