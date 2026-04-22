package org.sopt.domain.post.dto.request;

import org.sopt.domain.post.entity.BoardType;

public record CreatePostRequest(
        BoardType boardType,
        String title,
        String content,
        String author) {
}