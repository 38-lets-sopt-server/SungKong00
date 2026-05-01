package org.sopt.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;

import java.time.LocalDateTime;

@Schema(description = "게시글 조회 응답 DTO")
public record PostResponse(
        @Schema(description = "게시글 고유 ID", example = "1")
        Long id,

        @Schema(description = "게시판 종류", example = "FREE")
        BoardType boardType,

        @Schema(description = "게시글 제목", example = "게시글 제목입니다.")
        String title,

        @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
        String content,

        @Schema(description = "작성자 ID", example = "1")
        Long userId,

        @Schema(description = "생성 일시", example = "2026-05-01T15:30:00")
        LocalDateTime createdAt
) {
    public PostResponse(Post post) {
        this(post.getId(), post.getBoardType(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getCreatedAt());
    }

    @Override
    public String toString() {
        return "[" + id() + "] " + title() + " - " + userId() + " (" + createdAt() + ")\n" + content();
    }
}