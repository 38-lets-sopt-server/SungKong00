package org.sopt.exception.post;

import org.sopt.dto.response.ResponseCode;
import org.sopt.exception.BaseErrorCode;

import static org.sopt.dto.response.ResponseCode.*;

public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND(NOT_FOUND,"POST_404", "게시글을 찾을 수 없습니다."),
    POST_ALREADY_DELETED(BAD_REQUEST,"POST_400", "이미 삭제된 게시글입니다."),
    INVALID_POST_CONTENT(BAD_REQUEST,"POST_400", "게시글 내용이 유효하지 않습니다.");

    private final ResponseCode responseCode;
    private final String code;
    private final String message;

    PostErrorCode(ResponseCode responseCode, String code, String message) {
        this.responseCode = responseCode;
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return String.valueOf(code);
    }
    @Override
    public String getMessage() {
        return message;
    }
}
