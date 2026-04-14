package org.sopt.dto.response;

public enum StatusCode {
    OK("200", "성공"),
    BAD_REQUEST("400", "잘못된 요청"),
    NOT_FOUND("404", "찾을 수 없음"),
    INTERNAL_SERVER_ERROR("500", "서버 오류");

    private final String statusCode;
    private final String message;

    StatusCode(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
