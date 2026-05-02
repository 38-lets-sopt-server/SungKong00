package org.sopt.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 생성 응답 DTO")
public record CreatePostResponse(
        @Schema(description = "생성된 게시글의 고유 ID", example = "10")
        Long id
) {
}