package com.fn.ai.delivery.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final String SEQUENCE_KEY = "last_sequence:";

  public CacheRepository(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public long getLastDeliveryManagerSequence() {
    Object value = redisTemplate.opsForValue().get(SEQUENCE_KEY);
    return value == null ? 0 : ((Number) value).longValue();
  }

  public void setLastDeliveryManagerSequence(long sequence) {
    redisTemplate.opsForValue().set(SEQUENCE_KEY, sequence);
  }
}