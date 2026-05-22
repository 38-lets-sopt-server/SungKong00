package org.sopt.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.user.entity.User;

@Schema(description = "사용자 정보 응답 DTO")
public record UserResponse(

        @Schema(description = "사용자 ID", example = "1")
        long userId,

        @Schema(description = "사용자 닉네임", example = "sopt_user")
        String nickname,

        @Schema(description = "사용자 이메일", example = "example@email.com")
        String email
) {
        public static UserResponse from(User user) {
                return new UserResponse(
                        user.getId(),
                        user.getNickname(),
                        user.getEmail()
                );
        }
}
