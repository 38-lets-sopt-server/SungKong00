package org.sopt.dto.response;

import org.sopt.domain.Post;

// 게시글 조회 응답 (서버 → 클라이언트)
// 게시글 조회 응답 (서버 → 클라이언트)
public record PostResponse(Long id, String title, String content, String author, String createdAt) {
    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), post.getCreatedAt());
    }

    @Override
    public String toString() {
        return "[" + id() + "] " + title() + " - " + author() + " (" + createdAt() + ")\n" + content();
    }
}