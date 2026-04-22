package org.sopt.global.common.response;

public enum GlobalErrorCode implements BaseErrorCode {

    INTERNAL_SERVER_ERROR(500, "SERVER_500", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(400, "CLIENT_400", "잘못된 요청입니다."),
    UNAUTHORIZED(401, "CLIENT_401", "인증이 필요합니다."),
    FORBIDDEN(403, "CLIENT_403", "권한이 없습니다."),
    NOT_FOUND(404, "CLIENT_404", "요청한 리소스를 찾을 수 없습니다.");

    private final int httpStatusCode;
    private final String detailCode;
    private final String message;

    GlobalErrorCode(int httpStatusCode, String detailCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.detailCode = detailCode;
        this.message = message;
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
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
