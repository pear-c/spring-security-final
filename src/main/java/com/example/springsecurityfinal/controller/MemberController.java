package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.model.Member;
import com.example.springsecurityfinal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        memberService.createMember(member);
        return ResponseEntity.ok(member);
    }
}
