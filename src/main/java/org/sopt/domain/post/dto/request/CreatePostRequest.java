package org.sopt.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.post.entity.BoardType;

@Schema(description = "게시글 생성 요청 DTO")
public record CreatePostRequest(
        @Schema(description = "게시판 종류", example = "FREE")
        BoardType boardType,

        @Schema(description = "게시글 제목 (50자 이내)", example = "게시글 제목입니다.")
        String title,

        @Schema(description = "게시글 내용 (2000자 이내)", example = "게시글 내용입니다.")
        String content,

        @Schema(description = "작성자 ID", example = "1")
        Long userId
) {
}