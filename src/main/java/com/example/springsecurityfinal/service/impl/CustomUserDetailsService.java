package com.example.springsecurityfinal.service.impl;

import com.example.springsecurityfinal.domain.auth.AuthUser;
import com.example.springsecurityfinal.domain.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberServiceImpl memberServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberServiceImpl.getMemberEntity(username);
        if(memberEntity == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }
        return new AuthUser(memberEntity);
    }
}
