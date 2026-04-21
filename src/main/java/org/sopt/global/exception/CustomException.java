package org.sopt.global.exception;

import org.sopt.global.common.response.BaseErrorCode;

public class CustomException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetailCode() {
        return errorCode.getDetailCode();
    }

    public String getMessage() {
        return super.getMessage();
    }
}
