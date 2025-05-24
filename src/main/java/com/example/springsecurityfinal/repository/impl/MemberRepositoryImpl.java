package com.example.springsecurityfinal.repository.impl;

import com.example.springsecurityfinal.domain.member.MemberEntity;
import com.example.springsecurityfinal.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private static final String HASH_NAME = "Member:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean existsById(String id) {
        return redisTemplate.opsForHash().hasKey(HASH_NAME, id);
    }

    @Override
    public void save(MemberEntity memberEntity) {
        redisTemplate.opsForHash().put(HASH_NAME, memberEntity.getId(), memberEntity);
    }

    @Override
    public Optional<MemberEntity> findById(String id) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME, id);
        if(o == null) {
            return Optional.empty();
        }
        return Optional.of(objectMapper.convertValue(o, MemberEntity.class));
    }

    @Override
    public List<MemberEntity> findAll() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(HASH_NAME);
        List<MemberEntity> members = new ArrayList<>();
        for(Object value : entries.values()) {
            members.add(objectMapper.convertValue(value, MemberEntity.class));
        }
        return members;
    }
}
