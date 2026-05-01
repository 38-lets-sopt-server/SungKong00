package org.sopt.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.global.common.response.GlobalErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 에러 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException e) {
        log.warn("CustomException 발생: {}", e.getMessage());
        return BaseResponse.failure(e);
    }

    // 그 외의 예외 처리 (예: NullPointerException, IllegalArgumentException 등)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception e) {
        log.error("예상치 못 한 서버 에러!!", e);
        return BaseResponse.failure(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }
}