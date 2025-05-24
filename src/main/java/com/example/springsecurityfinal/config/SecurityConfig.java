package com.example.springsecurityfinal.config;

import com.example.springsecurityfinal.config.filter.UserAuthenticationFilter;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationFailureHandler;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationLogoutHandler;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationSuccessHandler;
import com.example.springsecurityfinal.service.impl.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailureHandler customAuthenticationFailureHandler, CustomAuthenticationLogoutHandler customAuthenticationLogoutHandler, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        // 필터 설정
        http.addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 로그인 설정
        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login/process")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .addLogoutHandler(customAuthenticationLogoutHandler)
                .logoutSuccessUrl("/auth/login")
        );

        // 권한 설정
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/admin-page/**").hasRole("ADMIN")
                        .requestMatchers("/member-page/**").hasRole("MEMBER")
                        .requestMatchers("/google-page/**").hasRole("GOOGLE")
                        .requestMatchers(HttpMethod.POST, "/members").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
        );

        // 예외 처리 핸들러
        http.exceptionHandling(exception ->
                exception.accessDeniedPage("/403")
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2Login(oauth2Login -> oauth2Login
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService)
                )
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
        );

        return http.build();
    }
}
