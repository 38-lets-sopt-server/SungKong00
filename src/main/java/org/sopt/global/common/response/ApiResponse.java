package org.sopt.global.common.response;

import org.sopt.global.exception.CustomException;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {

    private final boolean success;
    private final String code; // 상세 에러/성공 식별자 (예: POST_201)
    private final String message;
    private final T data;

    private ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

        // --- Success Factory Methods ---

    // 데이터가 있는 성공 (ResponseEntity를 통째로 반환)
    public static <T> ResponseEntity<ApiResponse<T>> success(BaseSuccessCode successCode, T data) {
        ApiResponse<T> response = new ApiResponse<>(true, successCode.getDetailCode(), successCode.getMessage(), data);
        return ResponseEntity.status(successCode.getHttpStatusCode()).body(response);
    }

    //  데이터가 없는 성공
    public static <T> ResponseEntity<ApiResponse<T>> success(BaseSuccessCode successCode) {
        ApiResponse<T> response = new ApiResponse<>(true, successCode.getDetailCode(), successCode.getMessage(), null);
        return ResponseEntity.status(successCode.getHttpStatusCode()).body(response);
    }

    // --- Failure Factory Methods ---

    // BaseErrorCode을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<ApiResponse<T>> failure(BaseErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>(false, errorCode.getDetailCode(), errorCode.getMessage(), null);
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }

    // CustomException을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<ApiResponse<T>> failure(CustomException exception) {
        BaseErrorCode errorCode = exception.getErrorCode();
        ApiResponse<T> response = new ApiResponse<>(false, errorCode.getDetailCode(), exception.getMessage(), null);
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }

    // Getters
    public boolean isSuccess() {
        return success;
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