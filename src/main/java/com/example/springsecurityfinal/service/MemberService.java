package com.example.springsecurityfinal.service;

import com.example.springsecurityfinal.exception.MemberAlreadyExistsException;
import com.example.springsecurityfinal.model.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    private Map<String, Member> memberMap = new HashMap<>();

    public void createMember(Member member) {
        if(memberMap.containsKey(member.getId())) {
            throw new MemberAlreadyExistsException();
        }
        memberMap.put(member.getId(), member);
    }

    public Member getMember(String id) {
        return memberMap.get(id);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(memberMap.values());
    }
}
