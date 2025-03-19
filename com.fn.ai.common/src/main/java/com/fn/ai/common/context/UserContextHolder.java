package com.fn.ai.common.context;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContextHolder {

  private static final ThreadLocal<UserContext> userHolder = new ThreadLocal<>();

  public static void setUserContext(UserContext userInfo) {
    userHolder.set(userInfo);
  }

  public static void clear() {
    userHolder.remove();
  }

  public static UUID getUserId() {
    return userHolder.get() != null ? userHolder.get().userId() : null;
  }

  public static String getUsername() {
    return userHolder.get() != null ? userHolder.get().username() : null;
  }

  public static UserRoleEnum getUserRole() {
    return userHolder.get() != null ? userHolder.get().userRole() : null;
  }

  public static UserContext getContext() {
    return userHolder.get();
  }

}
