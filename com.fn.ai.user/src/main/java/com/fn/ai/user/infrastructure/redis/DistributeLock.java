package com.fn.ai.user.infrastructure.redis;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLock {

  String key();

  long waitTime() default 5000L;

  long leaseTime() default 5000L;

}