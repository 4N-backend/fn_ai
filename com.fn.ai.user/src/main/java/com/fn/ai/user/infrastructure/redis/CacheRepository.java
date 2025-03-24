package com.fn.ai.user.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final String HUB_MANAGER_COUNT_KEY = "hub_manager_count:";

  public CacheRepository(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public long getHubManagerCount() {
    Object value = redisTemplate.opsForValue().get(HUB_MANAGER_COUNT_KEY);
    return value == null ? 0 : ((Number) value).longValue();
  }

  public void incrementHubManagerCount() {
    redisTemplate.opsForValue().increment(HUB_MANAGER_COUNT_KEY);
  }

  public void decrementHubManagerCount() {
    redisTemplate.opsForValue().decrement(HUB_MANAGER_COUNT_KEY);
  }
}