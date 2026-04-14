package org.sopt.exception;

import org.sopt.dto.response.StatusCode;

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

    public StatusCode getStatusCode() {
        return errorCode.getStatusCode();
    }

    public String getDetailCode() {
        return errorCode.getDetailCode();
    }

    public String getMessage() {
        return super.getMessage();
    }
}
