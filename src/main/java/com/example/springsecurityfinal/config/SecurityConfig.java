package com.example.springsecurityfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login/process")
                        .defaultSuccessUrl("/")
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

        return http.build();
    }
}
