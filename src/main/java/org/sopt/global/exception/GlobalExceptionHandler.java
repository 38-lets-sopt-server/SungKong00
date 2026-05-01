package org.sopt.global.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.global.common.response.GlobalErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 에러 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException e) {
        log.warn("CustomException 발생: {}", e.getMessage());
        return BaseResponse.failure(e);
    }

    // Bean Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<List<String>>> handleValidationException(MethodArgumentNotValidException e) {
        List<String> messages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> formatMessage(error.getField(), error.getDefaultMessage()))
                .toList();

        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST, messages);
    }

    // Bean Validation 예외 처리 (PathVariable/RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<List<String>>> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> messages = e.getConstraintViolations().stream()
                .map(violation -> formatMessage(extractLeafField(violation.getPropertyPath()), violation.getMessage()))
                .toList();

        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST, messages);
    }

    // RequestParam/PathVariable 타입 불일치 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<List<String>>> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = formatMessage(e.getName(), "잘못된 값입니다.");
        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST, List.of(message));
    }

    // 그 외의 예외 처리 (예: NullPointerException, IllegalArgumentException 등)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception e) {
        log.error("예상치 못 한 서버 에러!!", e);
        return BaseResponse.failure(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    private String formatMessage(String field, String message) {
        return field + ": " + message;
    }

    private String extractLeafField(Iterable<?> path) {
        String last = "parameter";
        for (Object node : path) {
            last = String.valueOf(node);
        }
        return last;
    }
}