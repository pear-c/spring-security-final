package com.example.springsecurityfinal.service;

import com.example.springsecurityfinal.model.Member;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {

    private Map<String, Member> memberMap = new HashMap<>();

    public void createMember(Member member) {
        memberMap.put(member.getId(), member);
    }
}
