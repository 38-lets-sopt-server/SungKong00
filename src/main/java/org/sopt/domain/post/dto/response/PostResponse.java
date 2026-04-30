package org.sopt.domain.post.dto.response;

import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.user.entity.User;

import java.time.LocalDateTime;

// 게시글 조회 응답
public record PostResponse(Long id, BoardType boardType, String title, String content, Long userId, LocalDateTime createdAt) {
    public PostResponse(Post post) {
        this(post.getId(), post.getBoardType(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getCreatedAt());
    }

    @Override
    public String toString() {
        return "[" + id() + "] " + title() + " - " + userId() + " (" + createdAt() + ")\n" + content();
    }
}