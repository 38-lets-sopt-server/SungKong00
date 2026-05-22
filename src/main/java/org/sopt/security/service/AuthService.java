package org.sopt.security.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.exception.UserErrorCode;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.CustomException;
import org.sopt.security.RefreshToken;
import org.sopt.security.dto.response.TokenResponse;
import org.sopt.security.exception.AuthErrorCode;
import org.sopt.security.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    public UserResponse loginWithCredentials(String email, String password) {
        // 로그인 검증: 이메일로 회원 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_CREDENTIALS));

        // 비밀번호 비교: 원문 vs BCrypt 해시
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        return UserResponse.from(user);
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        UserResponse member = loginWithCredentials(email, password);

        // 로그인 성공: Access/Refresh Token 발급
        String accessToken = jwtService.generateAccessToken(member.userId(), member.email());
        String refreshToken = jwtService.generateRefreshToken(member.userId());

        // Refresh Token 교체: 한 회원당 하나만 유지
        refreshTokenRepository.deleteByMemberId(member.userId());
        refreshTokenRepository.save(
                RefreshToken.of(member.userId(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse reissue(String refreshTokenValue) {
        // Refresh Token 검증: JWT 서명/만료 먼저 확인
        Long memberId = verifyRefreshToken(refreshTokenValue);

        // DB에 저장된 Refresh Token인지 확인
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        // 토큰 subject와 저장된 회원 id 비교
        if (!refreshToken.getMemberId().equals(memberId)) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN);
        }

        // DB 기준 만료 확인: 만료면 저장 토큰 삭제
        if (refreshToken.isExpired(LocalDateTime.now())) {
            refreshTokenRepository.deleteByMemberId(memberId);
            throw new CustomException(AuthErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        // 재발급 성공: Access 새로 발급, Refresh는 회전
        UserResponse member = getUserResponse(memberId);
        String newAccessToken = jwtService.generateAccessToken(member.userId(), member.email());
        String newRefreshToken = jwtService.generateRefreshToken(member.userId());

        refreshToken.rotate(newRefreshToken, refreshTokenExpiresInSeconds);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    public UserResponse getUserResponse(Long memberId) {
        // 인증된 id로 최신 회원 정보 조회
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        return UserResponse.from(member);
    }

    private Long verifyRefreshToken(String refreshTokenValue) {
        try {
            return jwtService.verifyAndGetMemberId(refreshTokenValue);
        } catch (RuntimeException e) {
            // JWT 라이브러리 예외를 우리 에러 코드로 변환
            throw new CustomException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
