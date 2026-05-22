package org.sopt.security;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private RefreshToken(Long memberId, String token, LocalDateTime expiresAt) {
        this.memberId = memberId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static RefreshToken of(Long memberId, String token, long expiresInSeconds) {
        // 처음 저장: 현재 시각 기준 만료 시간 계산
        return new RefreshToken(
                memberId,
                token,
                LocalDateTime.now().plusSeconds(expiresInSeconds)
        );
    }

    public void rotate(String newToken, long expiresInSeconds) {
        this.token = newToken;
        this.expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds);
    }
}
