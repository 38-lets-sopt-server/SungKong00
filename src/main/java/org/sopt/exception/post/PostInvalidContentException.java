package org.sopt.exception.post;

import org.sopt.exception.CustomException;

public class PostInvalidContentException extends CustomException {
    public PostInvalidContentException() {
        super(PostErrorCode.INVALID_POST_CONTENT);
    }
    public PostInvalidContentException(String message) {
        super(PostErrorCode.INVALID_POST_CONTENT, message);
    }
}
