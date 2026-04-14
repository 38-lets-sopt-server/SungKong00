package org.sopt.exception.post;

import org.sopt.exception.CustomException;

public class PostNotFoundException extends CustomException {
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
    public PostNotFoundException(String message) {
        super(PostErrorCode.POST_NOT_FOUND, message);
    }
    public PostNotFoundException(Long id) {
        super(PostErrorCode.POST_NOT_FOUND, "🚫 게시글을 찾을 수 없습니다. id: " + id);
    }
}

