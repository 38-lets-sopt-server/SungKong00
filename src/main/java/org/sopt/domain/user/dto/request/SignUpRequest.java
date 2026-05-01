package org.sopt.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 가입 요청 DTO")
public record SignUpRequest(

        @Schema(description = "사용자 닉네임", example = "NickName123")
        String nickname,

        @Schema(description = "사용자 이메일", example = "test@email.com")
        String email
) {
}
