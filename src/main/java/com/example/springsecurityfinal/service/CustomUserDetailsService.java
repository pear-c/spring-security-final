package com.example.springsecurityfinal.service;

import com.example.springsecurityfinal.domain.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberService.getMemberEntity(username);
        if(memberEntity == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }

        return User.builder()
                .username(memberEntity.getId())
                .password(memberEntity.getEncodedPassword())
                .roles(memberEntity.getRole().toString().toUpperCase())
                .build();
    }
}
