package com.fn.ai.common.config;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
          }
          if (context.username() != null) {
            template.header(USER_NAME, context.username());
          }
          if (context.userRole() != null) {
            template.header(USER_ROLE, context.userRole().toString());
          }
        }
      }
    };
  }
}