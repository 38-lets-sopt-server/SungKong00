package org.sopt.exception.post;

import org.sopt.dto.response.StatusCode;
import org.sopt.exception.BaseErrorCode;

import static org.sopt.dto.response.StatusCode.*;

public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND(NOT_FOUND,"POST_404", "게시글을 찾을 수 없습니다."),
    POST_ALREADY_DELETED(BAD_REQUEST,"POST_400", "이미 삭제된 게시글입니다."),
    INVALID_POST_CONTENT(BAD_REQUEST,"POST_400", "게시글 내용이 유효하지 않습니다.");

    private final StatusCode statusCode;
    private final String detailCode;
    private final String message;

    PostErrorCode(StatusCode statusCode, String detailCode, String message) {
        this.statusCode = statusCode;
        this.detailCode = detailCode;
        this.message = message;
    }

    @Override
    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String getDetailCode() {
        return detailCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
