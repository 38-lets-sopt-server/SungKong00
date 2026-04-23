package org.sopt.global.common.response;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.sopt.global.exception.CustomException;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {

    private final int status;
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(BaseCode code, T data) {
        this.status = code.getStatusNumber();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    private ApiResponse(BaseCode code, String customMessage, T data) {
        this.status = code.getStatusNumber();
        this.code = code.getCode();
        this.message = customMessage;
        this.data = data;
    }

        // --- Success Factory Methods ---

    // 데이터가 있는 성공 (ResponseEntity를 통째로 반환)
    public static <T> ResponseEntity<ApiResponse<T>> success(BaseSuccessCode successCode, T data) {
        ApiResponse<T> response = new ApiResponse<>(successCode, data);
        return ResponseEntity.status(successCode.getStatus()).body(response);
    }

    //  데이터가 없는 성공
    public static <T> ResponseEntity<ApiResponse<T>> success(BaseSuccessCode successCode) {
        ApiResponse<T> response = new ApiResponse<>(successCode, null);
        return ResponseEntity.status(successCode.getStatus()).body(response);
    }

    //

    // --- Failure Factory Methods ---

    // BaseErrorCode을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<ApiResponse<T>> failure(BaseErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>(errorCode, null);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // CustomException을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<ApiResponse<T>> failure(CustomException exception) {
        BaseErrorCode errorCode = exception.getErrorCode();
        ApiResponse<T> response = new ApiResponse<>(errorCode, exception.getMessage(), null);
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