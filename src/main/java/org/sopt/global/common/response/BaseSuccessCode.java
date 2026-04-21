package org.sopt.global.common.response;

public interface BaseSuccessCode {
    int getHttpStatusCode();
    String getDetailCode();
    String getMessage();
}