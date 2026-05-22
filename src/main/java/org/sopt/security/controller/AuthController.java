package org.sopt.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.global.common.response.GlobalErrorCode;
import org.sopt.global.common.response.GlobalSuccessCode;
import org.sopt.global.exception.CustomException;
import org.sopt.security.dto.request.LoginRequest;
import org.sopt.security.dto.request.ReissueRequest;
import org.sopt.security.dto.response.TokenResponse;
import org.sopt.security.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        // 로그인: 이메일/비밀번호 확인 후 토큰 발급
        TokenResponse tokens = authService.login(request.email(), request.password());

        return BaseResponse.success(GlobalSuccessCode.CREATED, tokens);
    }

    @Operation(summary = "토큰 재발급 (Refresh Token 회전)")
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenResponse>> reissue(
            @Valid @RequestBody ReissueRequest request
    ) {
        // 재발급: Refresh Token으로 새 토큰 묶음 발급
        TokenResponse tokens = authService.reissue(request.refreshToken());

        return BaseResponse.success(GlobalSuccessCode.CREATED, tokens);
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {

        // SecurityContext의 인증 id로 내 정보 조회
        Long memberId = getAuthenticatedMemberId(authentication);
        UserResponse member = authService.getUserResponse(memberId);

        return BaseResponse.success(GlobalSuccessCode.SUCCESS, member);
    }

    @Operation(summary = "로그아웃 (Refresh Token 삭제)")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        // 로그아웃: 현재 Access Token 차단 + Refresh Token 삭제
        authService.logout(getAuthenticatedMemberId(authentication), extractBearerToken(authorization));

        return BaseResponse.success(GlobalSuccessCode.SUCCESS);
    }

    private Long getAuthenticatedMemberId(Authentication authentication) {
        // 필터에서 넣은 principal 문자열을 Long id로 변환
        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
        }

        try {
            return Long.parseLong(authentication.getName());
        } catch (NumberFormatException e) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    private String extractBearerToken(String authorization) {
        // Authorization: Bearer xxx 형식만 허용
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
        }
        return authorization.substring("Bearer ".length()).trim();
    }
}
