package com.fn.ai.user.infrastructure.redis;

import com.fn.ai.user.infrastructure.redis.exception.RedissonLockAcquireFailedException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DistributeLockAspect {

  private final RedissonClient redissonClient;

  public DistributeLockAspect(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @Around("@annotation(distributeLock)")
  public Object around(ProceedingJoinPoint joinPoint, DistributeLock distributeLock) throws Throwable {
    String key = distributeLock.key();
    if (key == null || key.isEmpty()) {
      key = joinPoint.getSignature().toShortString();
    }
    RLock lock = redissonClient.getLock(key);
    boolean acquired = lock.tryLock(distributeLock.waitTime(), distributeLock.leaseTime(),
        TimeUnit.SECONDS);
    if (!acquired) {
      throw new RedissonLockAcquireFailedException();
    }
    try {
      return joinPoint.proceed();
    } finally {
      lock.unlock();
    }
  }
}