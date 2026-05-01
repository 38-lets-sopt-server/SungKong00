package org.sopt.domain.post.exception;

import org.sopt.global.common.response.BaseErrorCode;
import org.springframework.http.HttpStatus;


public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"POST_404", "게시글을 찾을 수 없습니다."),
    INVALID_POST_CONTENT(HttpStatus.BAD_REQUEST,"POST_400", "게시글 내용이 유효하지 않습니다.");

    private final HttpStatus status;   // HttpStatus는 int를 사용하여 HttpStatus 의존 제거
    private final String code;
    private final String message;

    PostErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
