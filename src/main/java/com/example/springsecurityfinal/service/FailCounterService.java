package com.example.springsecurityfinal.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FailCounterService {
    private static final Map<String, Long> failCounter = new HashMap<>();

    public Long increaseAndGetFailCount(String id) {
        if (failCounter.containsKey(id)) {
            failCounter.put(id, failCounter.get(id) + 1);
            return failCounter.get(id);
        } else {
            failCounter.put(id, 1L);
            return 1L;
        }
    }

    public void resetFailCounter(String id) {
        failCounter.remove(id);
    }
}
