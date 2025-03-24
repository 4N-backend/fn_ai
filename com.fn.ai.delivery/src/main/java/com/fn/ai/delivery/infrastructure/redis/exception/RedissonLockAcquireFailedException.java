package com.fn.ai.delivery.infrastructure.redis.exception;

import static org.springframework.http.HttpStatus.LOCKED;

import com.fn.ai.common.exception.BaseException;

public class RedissonLockAcquireFailedException extends BaseException {

  public static final String MESSAGE = "Redisson 락 획득에 실패하였습니다.";

  public RedissonLockAcquireFailedException() {
    super(LOCKED, MESSAGE);
  }
}
