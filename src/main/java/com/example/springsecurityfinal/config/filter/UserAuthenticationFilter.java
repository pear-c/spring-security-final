package com.example.springsecurityfinal.config.filter;

import com.example.springsecurityfinal.domain.auth.AuthUser;
import com.example.springsecurityfinal.domain.member.MemberEntity;
import com.example.springsecurityfinal.service.impl.FailCounterService;
import com.example.springsecurityfinal.service.impl.MemberServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberServiceImpl memberServiceImpl;
    private final FailCounterService failCounterService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id != null && failCounterService.isBlocked(id)) {
            String key = "Blocked:" + id;
            Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            System.out.println("로그인 시도 제한: " + ttl + "초 후 다시 시도해주세요.");
            request.getRequestDispatcher("/auth/login").forward(request, response);
            return;
        }

        String sessionId = null;

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("sessionId")) {
                sessionId = cookie.getValue();
            }
        }

        if(sessionId != null) {
            String memberId = (String) redisTemplate.opsForValue().get(sessionId);

            if(memberId != null) {
                MemberEntity memberEntity = memberServiceImpl.getMemberEntity(memberId);
                AuthUser authUser = new AuthUser(memberEntity);
                Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
