package org.sopt.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.domain.post.entity.BoardType;

@Schema(description = "게시글 생성 요청 DTO")
public record CreatePostRequest(
        @Schema(description = "게시판 종류", example = "FREE")
        @NotNull(message = "게시판 종류는 필수입니다.")
        BoardType boardType,

        @Schema(description = "게시글 제목 (50자 이내)", example = "게시글 제목입니다.")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 50, message = "제목은 50자 이내로 작성해주세요.")
        String title,

        @Schema(description = "게시글 내용 (2000자 이내)", example = "게시글 내용입니다.")
        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요.")
        String content
) {
}
