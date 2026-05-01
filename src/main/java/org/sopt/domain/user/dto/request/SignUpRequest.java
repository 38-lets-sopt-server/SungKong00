package org.sopt.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "회원 가입 요청 DTO")
public record SignUpRequest(

        @Schema(description = "사용자 닉네임", example = "NickName123")
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 30, message = "닉네임은 30자 이내로 작성해주세요.")
        String nickname,

        @Schema(description = "사용자 이메일", example = "test@email.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        String email
) {
}
