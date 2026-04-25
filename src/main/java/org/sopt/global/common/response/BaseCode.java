package org.sopt.global.common.response;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    HttpStatus getStatus();
    String getCode();
    String getMessage();

    default int getStatusNumber() {
        return getStatus().value();
    }
}
