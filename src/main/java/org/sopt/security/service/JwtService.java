package org.sopt.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final long accessTokenExpiresInSeconds;
    private final long refreshTokenExpiresInSeconds;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-expires-in-seconds:1800}") long accessTokenExpiresInSeconds,
            @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}") long refreshTokenExpiresInSeconds
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
        this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
    }

    public String generateAccessToken(Long memberId, String email) {
        // Access Token: API 인증용, 짧게 사용
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(memberId))
                .withClaim("email", email)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(accessTokenExpiresInSeconds)))
                .sign(algorithm);
    }

    public String generateRefreshToken(Long memberId) {
        // Refresh Token: Access 재발급용, 길게 사용
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(memberId))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(refreshTokenExpiresInSeconds)))
                .sign(algorithm);
    }

    public Long verifyAndGetMemberId(String token) {
        // 토큰 검증 후 subject를 회원 id로 사용
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        try {
            return Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("JWT의 회원 정보가 올바르지 않습니다.");
        }
    }

    public LocalDateTime verifyAndGetExpiresAt(String token) {
        // 로그아웃 블랙리스트 만료 시간 계산용
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getExpiresAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
