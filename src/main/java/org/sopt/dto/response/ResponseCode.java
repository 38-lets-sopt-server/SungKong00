package org.sopt.dto.response;

public enum ResponseCode {
    OK("200", "성공"),
    BAD_REQUEST("400", "잘못된 요청"),
    NOT_FOUND("404", "찾을 수 없음"),
    INTERNAL_SERVER_ERROR("500", "서버 오류");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
