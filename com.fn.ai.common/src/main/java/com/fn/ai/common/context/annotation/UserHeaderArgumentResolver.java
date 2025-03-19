package com.fn.ai.common.context.annotation;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserRoleEnum;
import java.util.Objects;
import java.util.UUID;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserHeaderArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUserInfo.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {

    UUID userId = UUID.fromString(Objects.requireNonNull(
        webRequest.getHeader("X-User-Id")));
    String username = webRequest.getHeader("X-User-Name");
    UserRoleEnum userRole = UserRoleEnum.valueOf(webRequest.getHeader("X-User-Role"));

    return UserContext.builder()
        .userId(userId)
        .username(username)
        .userRole(userRole)
        .build();
  }

}
