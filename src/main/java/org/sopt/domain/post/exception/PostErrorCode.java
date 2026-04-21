package org.sopt.domain.post.exception;

import org.sopt.global.common.response.BaseErrorCode;


public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND(404 ,"POST_404", "게시글을 찾을 수 없습니다."),
    POST_ALREADY_DELETED(400,"POST_400", "이미 삭제된 게시글입니다."),
    INVALID_POST_CONTENT(400,"POST_400", "게시글 내용이 유효하지 않습니다.");

    private final int httpStatus;   // HttpStatus는 int를 사용하여 HttpStatus 의존 제거
    private final String detailCode;
    private final String message;

    PostErrorCode(int statusCode, String detailCode, String message) {
        this.httpStatus = statusCode;
        this.detailCode = detailCode;
        this.message = message;
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatus;
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
