package com.example.springsecurityfinal.service.impl;

import com.example.springsecurityfinal.domain.auth.AuthUser;
import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.domain.member.MemberEntity;
import com.example.springsecurityfinal.domain.member.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    MemberServiceImpl memberServiceImpl;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("사용자 존재 시 UserDetails 반환")
    void loadUserByUsername_success() {
        // given
        Member member = new Member("kim", "김철수", "1234", 26, Role.MEMBER);
        MemberEntity memberEntity = new MemberEntity(member, "1234");
        given(memberServiceImpl.getMemberEntity("kim")).willReturn(memberEntity);

        // when
        UserDetails result = customUserDetailsService.loadUserByUsername("kim");

        // then
        assertThat(result).isInstanceOf(AuthUser.class);
        assertThat(result.getUsername()).isEqualTo("kim");
    }

    @Test
    @DisplayName("사용자 없을 경우 예외 발생")
    void loadUserByUsername_fail() {
        // given
        given(memberServiceImpl.getMemberEntity("unknown")).willReturn(null);

        // when & then
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername("unknown"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("사용자 없음");
    }
}
