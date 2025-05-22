package com.example.springsecurityfinal.config;

import com.example.springsecurityfinal.config.filter.UserAuthenticationFilter;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationFailureHandler;
import com.example.springsecurityfinal.config.handler.CustomAuthenticationSuccessHandler;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) throws Exception {

        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login/process")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
        );

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/admin-page/**").hasRole("ADMIN")
                        .requestMatchers("/member-page/**").hasRole("MEMBER")
                        .requestMatchers("/google-page/**").hasRole("GOOGLE")
                        .requestMatchers(HttpMethod.POST, "/members").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
        );

        http.exceptionHandling(exception ->
                exception.accessDeniedPage("/403")
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
