package org.sopt.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.security.repository.AccessTokenBlacklistRepository;
import org.sopt.security.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccessTokenBlacklistRepository accessTokenBlacklistRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            // Bearer 토큰만 꺼내서 검증
            String token = header.substring("Bearer ".length()).trim();
            try {
                // 로그아웃된 Access Token이면 인증 처리 안 함
                if (accessTokenBlacklistRepository.existsByToken(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // JWT 검증 성공: SecurityContext에 회원 id 저장
                Long memberId = jwtService.verifyAndGetMemberId(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        String.valueOf(memberId), null, Collections.emptyList());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (IllegalArgumentException | JWTVerificationException e) {
                // 잘못된 토큰: 인증 없이 통과, 최종 차단은 SecurityConfig가 처리
            }
        }

        filterChain.doFilter(request, response);
    }
}
