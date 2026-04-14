package org.sopt.exception;

import org.sopt.dto.response.StatusCode;

public interface BaseErrorCode {
    StatusCode getStatusCode();
    String getDetailCode();
    String getMessage();
}
