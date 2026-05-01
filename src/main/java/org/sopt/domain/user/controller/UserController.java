package org.sopt.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.request.SignUpRequest;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.domain.user.service.UserService;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.global.common.response.GlobalSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 API", description = "사용자 정보 관련 API")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<UserResponse>> signUp(
            @RequestBody SignUpRequest request
    ) {
        UserResponse response = userService.signUp(request);
        return BaseResponse.success(GlobalSuccessCode.CREATED, response);
    }
}
