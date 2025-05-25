package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.config.filter.UserAuthenticationFilter;
import com.example.springsecurityfinal.domain.auth.AuthUser;
import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.domain.member.MemberEntity;
import com.example.springsecurityfinal.domain.member.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "kim", roles = {"MEMBER"})
    void homeWithAuthUser() throws Exception {
        // given
        Member member = new Member("kim", "김철수", "1234", 26, Role.MEMBER);
        MemberEntity memberEntity = new MemberEntity(member, "1234");
        AuthUser authUser = new AuthUser(memberEntity);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities())
        );

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginName"))
                .andExpect(view().name("home"));
    }
}
