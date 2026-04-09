package org.sopt.exception.post;

import org.sopt.exception.CustomException;

public class PostNotFoundException extends CustomException {
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}

