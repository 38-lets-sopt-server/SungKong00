package org.sopt.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.global.common.response.GlobalSuccessCode;
import org.sopt.security.dto.response.TokenResponse;
import org.sopt.security.service.AuthService;
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
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        TokenResponse tokens = authService.login(email, password);

        return BaseResponse.success(GlobalSuccessCode.CREATED, tokens);
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long memberId = Long.parseLong(authentication.getName());
        UserResponse member = authService.getUserResponse(memberId);

        return BaseResponse.success(GlobalSuccessCode.SUCCESS, member);
    }
}
