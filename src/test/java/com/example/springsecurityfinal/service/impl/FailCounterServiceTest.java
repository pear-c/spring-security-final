package com.example.springsecurityfinal.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class FailCounterServiceTest {

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ValueOperations<String, Object> valueOperations;

    @InjectMocks
    FailCounterService failCounterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
    }

    @Test
    @DisplayName("실패 횟수 가져오기 - 값이 있을 때")
    void getFailCount_withValue() {
        String id = "kim";
        given(valueOperations.get("Fail:" + id)).willReturn("3");

        Long failCount = failCounterService.getFailCount(id);

        assertThat(failCount).isEqualTo(3L);
    }

    @Test
    @DisplayName("실패 횟수 가져오기 - 값이 없을 때")
    void getFailCount_nullValue() {
        String id = "kim";
        given(valueOperations.get("Fail:" + id)).willReturn(null);

        Long failCount = failCounterService.getFailCount(id);

        assertThat(failCount).isEqualTo(0L);
    }

    @Test
    @DisplayName("실패 횟수 초기화")
    void resetFailCounterTest() {
        String id = "kim";

        failCounterService.resetFailCounter(id);

        verify(redisTemplate).delete("Fail:" + id);
    }

    @Test
    @DisplayName("로그인 차단")
    void blockLoginTest() {
        String id = "kim";

        failCounterService.blockLogin(id);

        verify(valueOperations).set("Blocked:" + id, "blocked", Duration.ofSeconds(60));
    }

    @Test
    @DisplayName("차단 여부 확인 - 차단됨")
    void isBlocked_true() {
        String id = "kim";
        given(redisTemplate.hasKey("Blocked:" + id)).willReturn(true);

        boolean result = failCounterService.isBlocked(id);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차단 여부 확인 - 차단되지 않음")
    void isBlocked_false() {
        String id = "kim";
        given(redisTemplate.hasKey("Blocked:" + id)).willReturn(false);

        boolean result = failCounterService.isBlocked(id);

        assertThat(result).isFalse();
    }
}
