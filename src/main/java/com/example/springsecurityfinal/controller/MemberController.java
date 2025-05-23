package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.service.MemberService;
import com.example.springsecurityfinal.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        memberService.createMember(member);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable String id) {
        Member member = memberService.getMember(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getMembers(Pageable pageable) {
        List<Member> members = memberService.getMembers();
        return ResponseEntity.ok(members);
    }
}
