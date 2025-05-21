package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.model.Member;
import com.example.springsecurityfinal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        memberService.createMember(member);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable String id) {
        Member member = memberService.getMember(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers() {
        List<Member> members = memberService.getMembers();
        return ResponseEntity.ok(members);
    }
}
