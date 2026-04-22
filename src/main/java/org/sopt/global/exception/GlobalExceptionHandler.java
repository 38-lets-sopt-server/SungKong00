package org.sopt.global.exception;

import org.sopt.global.common.response.ApiResponse;
import org.sopt.global.common.response.GlobalErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 에러 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        return ApiResponse.failure(e);
    }

    // 그 외의 예외 처리 (예: NullPointerException, IllegalArgumentException 등)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
        e.printStackTrace();
        return ApiResponse.failure(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }
}