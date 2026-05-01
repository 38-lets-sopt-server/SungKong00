package org.sopt.global.common.response;

import org.sopt.global.exception.CustomException;
import org.springframework.http.ResponseEntity;

public class BaseResponse<T> {

    private final int status;
    private final String code;
    private final String message;
    private final T data;

    private BaseResponse(BaseCode code, T data) {
        this.status = code.getStatusNumber();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    private BaseResponse(BaseCode code, String customMessage, T data) {
        this.status = code.getStatusNumber();
        this.code = code.getCode();
        this.message = customMessage;
        this.data = data;
    }

        // --- Success Factory Methods ---

    // 데이터가 있는 성공 (ResponseEntity를 통째로 반환)
    public static <T> ResponseEntity<BaseResponse<T>> success(BaseSuccessCode successCode, T data) {
        BaseResponse<T> response = new BaseResponse<>(successCode, data);
        return ResponseEntity.status(successCode.getStatus()).body(response);
    }

    //  데이터가 없는 성공
    public static <T> ResponseEntity<BaseResponse<T>> success(BaseSuccessCode successCode) {
        return success(successCode, null);
    }


    // --- Failure Factory Methods ---

    // BaseErrorCode을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<BaseResponse<T>> failure(BaseErrorCode errorCode) {
        BaseResponse<T> response = new BaseResponse<>(errorCode, null);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // CustomException을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<BaseResponse<T>> failure(CustomException exception) {
        BaseErrorCode errorCode = exception.getErrorCode();
        BaseResponse<T> response = new BaseResponse<>(errorCode, exception.getMessage(), null);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // Getters

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}