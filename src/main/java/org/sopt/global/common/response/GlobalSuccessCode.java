package org.sopt.global.common.response;

import org.springframework.http.HttpStatus;

public enum GlobalSuccessCode implements BaseSuccessCode{

    SUCCESS(HttpStatus.OK, "SUCCESS_200", "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "SUCCESS_201", "성공적으로 생성/저장 되었습니다."),
    UPDATED(HttpStatus.OK, "SUCCESS_200", "성공적으로 수정되었습니다."),
    DELETED(HttpStatus.OK, "SUCCESS_200", "성공적으로 삭제되었습니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    GlobalSuccessCode(HttpStatus status, String code, String message) {
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
