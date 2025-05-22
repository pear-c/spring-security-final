package com.example.springsecurityfinal.service;

import com.example.springsecurityfinal.exception.MemberAlreadyExistsException;
import com.example.springsecurityfinal.domain.Member;
import com.example.springsecurityfinal.domain.MemberEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final static String HASH_NAME = "Member:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public void createMember(Member member) {
        if(redisTemplate.opsForHash().hasKey(HASH_NAME, member.getId())) {
            throw new MemberAlreadyExistsException();
        }
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        MemberEntity memberEntity = new MemberEntity(member, encodedPassword);
        redisTemplate.opsForHash().put(HASH_NAME, memberEntity.getId(), memberEntity);
    }

    public Member getMember(String id) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME, id);
        MemberEntity memberEntity = objectMapper.convertValue(o, MemberEntity.class);
        return new Member(
                memberEntity.getId(),
                memberEntity.getName(),
                memberEntity.getEncodedPassword(),
                memberEntity.getAge(),
                memberEntity.getRole()
        );
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(HASH_NAME);
        for(Object value : entries.values()) {
            MemberEntity memberEntity = objectMapper.convertValue(value, MemberEntity.class);
            members.add(getMember(memberEntity.getId()));
        }
        return members;
    }

    public MemberEntity getMemberEntity(String userName) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME, userName);
        return objectMapper.convertValue(o, MemberEntity.class);
    }
}
