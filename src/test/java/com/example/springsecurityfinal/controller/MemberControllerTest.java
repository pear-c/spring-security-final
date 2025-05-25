package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.domain.member.Member;
import com.example.springsecurityfinal.domain.member.Role;
import com.example.springsecurityfinal.service.MemberService;
import com.example.springsecurityfinal.service.impl.FailCounterService;
import com.example.springsecurityfinal.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberServiceImpl;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private RedisTemplate<String, Object> redisTemplate;
    @MockBean
    private FailCounterService failCounterService;

    @Test
    @WithMockUser(username = "kim", roles = {"MEMBER"})
    void getMemberSuccess() throws Exception {
        Member mockMember = new Member("kim", "김철수", "12345", 26, Role.valueOf("MEMBER"));

        given(memberServiceImpl.getMember("kim")).willReturn(mockMember);

        mockMvc.perform(get("/members/kim"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("kim"))
                .andExpect(jsonPath("$.name").value("김철수"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.role").value("member"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getMembersSuccess() throws Exception {
        List<Member> mockMembers = List.of(
                new Member("kim", "김철수", "12345", 26, Role.MEMBER),
                new Member("lee", "이영희", "67890", 30, Role.MEMBER)
        );

        given(memberServiceImpl.getMembers()).willReturn(mockMembers);

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("kim"))
                .andExpect(jsonPath("$[1].id").value("lee"));
    }
}