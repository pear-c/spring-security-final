package com.example.springsecurityfinal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class FailCounterService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String FAIL_COUNT_KEY = "Fail:";
    private static final String BLOCK_KEY = "Blocked:";


    public void increaseFailCount(String id) {
        String key = FAIL_COUNT_KEY + id;
        redisTemplate.opsForValue().increment(key);

        if(getFailCount(id) >= 5) {
            blockLogin(id);
        }
    }

    public Long getFailCount(String id) {
        String key = FAIL_COUNT_KEY + id;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? 0L : Long.parseLong(value.toString());
    }

    public void resetFailCounter(String id) {
        redisTemplate.delete(FAIL_COUNT_KEY + id);
    }

    public void blockLogin(String id) {
        redisTemplate.opsForValue().set(BLOCK_KEY + id, "blocked", Duration.ofSeconds(60));
    }

    public boolean isBlocked(String id) {
        return redisTemplate.hasKey(BLOCK_KEY + id);
    }
}
