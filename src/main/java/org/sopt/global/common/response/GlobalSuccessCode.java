package org.sopt.global.common.response;

public enum GlobalSuccessCode implements BaseSuccessCode{

    SUCCESS(200, "SUCCESS_200", "요청이 성공적으로 처리되었습니다."),
    CREATED(201, "SUCCESS_201", "성공적으로 생성/저장 되었습니다."),
    UPDATED(200, "SUCCESS_200", "성공적으로 수정되었습니다."),
    DLELETED(204, "SUCCESS_204", "성공적으로 삭제되었습니다.")
    ;


    private final int httpStatusCode;
    private final String detailCode;
    private final String message;

    GlobalSuccessCode(int httpStatusCode, String detailCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.detailCode = detailCode;
        this.message = message;
    }

    @Override
    public int getHttpStatusCode() {
        return  httpStatusCode;
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
