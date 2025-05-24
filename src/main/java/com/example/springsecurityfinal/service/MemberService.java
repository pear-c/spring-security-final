package com.example.springsecurityfinal.service;

import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.domain.member.MemberEntity;

import java.util.List;

public interface MemberService {
    void createMember(Member member);
    Member getMember(String id);
    List<Member> getMembers();
    MemberEntity getMemberEntity(String id);
}
