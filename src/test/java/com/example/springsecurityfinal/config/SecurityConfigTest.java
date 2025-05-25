package com.example.springsecurityfinal.config;

import com.example.springsecurityfinal.config.filter.UserAuthenticationFilter;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationFailureHandler;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationLogoutHandler;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationSuccessHandler;
import com.example.springsecurityfinal.service.impl.CustomOAuth2UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void loginPageAccess() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void adminPageWithoutLogin() throws Exception {
        mockMvc.perform(get("/admin-page"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void adminPageWithAdminLogin() throws Exception {
        mockMvc.perform(get("/admin-page"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"MEMBER"})
    @Test
    void adminPageWithMemberLogin() throws Exception {
        mockMvc.perform(get("/admin-page"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "member", roles = {"MEMBER"})
    @Test
    void memberPageWithMemberLogin() throws Exception {
        mockMvc.perform(get("/member-page"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "googleUser", roles = {"GOOGLE"})
    @Test
    void googlePageWithGoogleLogin() throws Exception {
        mockMvc.perform(get("/google-page"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"MEMBER"})
    @Test
    void googlePageWithMemberLogin() throws Exception {
        mockMvc.perform(get("/google-page"))
                .andExpect(status().isForbidden());
    }
}