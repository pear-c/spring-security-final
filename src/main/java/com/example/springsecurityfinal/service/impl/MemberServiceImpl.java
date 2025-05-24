package com.example.springsecurityfinal.service.impl;

import com.example.springsecurityfinal.exception.MemberAlreadyExistsException;
import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.domain.member.MemberEntity;
import com.example.springsecurityfinal.repository.MemberRepository;
import com.example.springsecurityfinal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createMember(Member member) {
        if(memberRepository.existsById(member.getId())) {
            throw new MemberAlreadyExistsException();
        }
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        MemberEntity memberEntity = new MemberEntity(member, encodedPassword);
        memberRepository.save(memberEntity);
    }

    @Override
    public Member getMember(String id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElse(null);
        return toMember(memberEntity);
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll().stream()
                .map(this::toMember)
                .collect(Collectors.toList());
    }

    @Override
    public MemberEntity getMemberEntity(String id) {
        return memberRepository.findById(id).orElse(null);
    }

    private Member toMember(MemberEntity memberEntity) {
        return new Member(
                memberEntity.getId(),
                memberEntity.getName(),
                memberEntity.getEncodedPassword(),
                memberEntity.getAge(),
                memberEntity.getRole()
        );
    }
}
