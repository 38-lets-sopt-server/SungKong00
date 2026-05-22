package org.sopt.security.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.security.RefreshToken;
import org.sopt.security.dto.response.TokenResponse;
import org.sopt.security.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    public UserResponse loginWithCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return UserResponse.from(user);
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        UserResponse member = loginWithCredentials(email, password);

        String accessToken = jwtService.generateAccessToken(member.userId(), member.email());
        String refreshToken = jwtService.generateRefreshToken(member.userId());

        // 기존 Refresh Token 삭제 후 새로 저장
        refreshTokenRepository.deleteByMemberId(member.userId());
        refreshTokenRepository.save(
                RefreshToken.of(member.userId(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    public UserResponse getUserResponse(Long memberId) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return UserResponse.from(member);
    }
}