package org.sopt.global.common.response;


public interface BaseErrorCode {
    int getHttpStatusCode();
    String getDetailCode();
    String getMessage();
}
