package com.example.springsecurityfinal.domain.auth;

import com.example.springsecurityfinal.domain.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class AuthUser implements UserDetails {

    private final MemberEntity memberEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + memberEntity.getRole().name()));
    }

    @Override
    public String getPassword() {
        return memberEntity.getEncodedPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getId();
    }

    public String getUserName() {
        return memberEntity.getName();
    }
}
