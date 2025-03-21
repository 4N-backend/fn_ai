package com.fn.ai.common.config.aop;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.UserContextHolder;
import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.common.exception.BaseException;
import com.fn.ai.common.exception.code.CommonResponseCode;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {

  @Before("@annotation(RequireAuthorization)")
  public void checkAuthorization(JoinPoint joinPoint, RequireAuthorization RequireAuthorization) {
    UserContext userContext = UserContextHolder.getContext();
    if (userContext == null || !hasAnyRole(userContext.userRole(), RequireAuthorization.value())) {
      throw new BaseException(CommonResponseCode.FORBIDDEN.getCode(), "권한이 없습니다.");
    }
  }

  private boolean hasAnyRole(UserRoleEnum userRole, String[] requiredRoles) {
    System.out.println(userRole.name());
    return Arrays.stream(requiredRoles)
        .anyMatch(role -> role.equalsIgnoreCase(userRole.name()));
  }

}
