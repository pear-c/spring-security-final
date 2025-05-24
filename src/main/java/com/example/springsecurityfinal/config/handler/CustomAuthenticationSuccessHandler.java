package com.example.springsecurityfinal.config.handler;

import com.example.springsecurityfinal.service.impl.FailCounterService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final FailCounterService failCounterService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 기존 세션 무효화
        request.getSession().invalidate();

        // 쿠키에 세션 id 저장 및 생성
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Redis에 사용자 정보 저장
        redisTemplate.opsForValue().set(sessionId, authentication.getName());
        failCounterService.resetFailCounter(authentication.getName());

        response.sendRedirect("/");
    }
}
