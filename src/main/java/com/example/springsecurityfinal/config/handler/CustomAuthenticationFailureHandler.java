package com.example.springsecurityfinal.config.handler;

import com.example.springsecurityfinal.service.FailCounterService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final FailCounterService failCounterService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String id = request.getParameter("id");

        failCounterService.increaseFailCount(id);
        Long failCount = failCounterService.getFailCount(id);

        if(failCount >= 5) {
            System.out.println("로그인 5회 실패로 60초간 차단되었습니다.");
        } else {
            System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
        }

        request.getRequestDispatcher("/auth/login").forward(request, response);
    }
}
