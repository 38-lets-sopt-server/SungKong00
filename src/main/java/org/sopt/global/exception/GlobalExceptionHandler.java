package org.sopt.global.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.sopt.global.common.response.BaseResponse;
import org.sopt.global.common.response.GlobalErrorCode;
import org.sopt.global.common.response.ValidationErrorResponse;
import org.sopt.global.common.response.ValidationErrorResponse.FieldErrorDetail;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


// 생성형 AI의 도움을 많이 받았음. 각 코드를 이해는 하였으나, 실제 혼자 구현할 수 있도록 숙달이 많이 필요함.

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 에러 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException e) {
        log.warn("CustomException 발생: {}", e.getMessage());
        return BaseResponse.failure(e);
    }

    // Bean Validation 예외 처리 - 요청 바디 검증
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<ValidationErrorResponse>> handleValidationException(MethodArgumentNotValidException e) {
        List<FieldErrorDetail> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        fieldError -> fieldError.getField(),
                        Collectors.mapping(fieldError -> fieldError.getDefaultMessage(), Collectors.toList())))
                .entrySet().stream()
                .map(entry -> new FieldErrorDetail(entry.getKey(), entry.getValue()))
                .toList();

        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST, new ValidationErrorResponse(errors));
    }

    // Bean Validation 예외 처리 (PathVariable/RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<ValidationErrorResponse>> handleConstraintViolationException(ConstraintViolationException e) {
        List<FieldErrorDetail> errors = e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(
                        violation -> extractLeafField(violation.getPropertyPath()),
                        Collectors.mapping(violation -> violation.getMessage(), Collectors.toList())))
                .entrySet().stream()
                .map(entry -> new FieldErrorDetail(entry.getKey(), entry.getValue()))
                .toList();

        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST, new ValidationErrorResponse(errors));
    }

    // RequestParam/PathVariable 타입 불일치 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<ValidationErrorResponse>> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        List<FieldErrorDetail> errors = List.of(new FieldErrorDetail(e.getName(), List.of("잘못된 값입니다.")));
        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST, new ValidationErrorResponse(errors));
    }

    // JSON 파싱 실패 처리 (잘못된 JSON, enum 매핑 실패 등)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("잘못된 요청 형식: {}", e.getMessage());
        return BaseResponse.failure(GlobalErrorCode.BAD_REQUEST);
    }

    // DB 제약 위반 처리 (unique 등) - 도메인 무관 안전망
    @ExceptionHandler({DataIntegrityViolationException.class, JpaSystemException.class})
    public ResponseEntity<BaseResponse<Void>> handleDataIntegrityException(Exception e) {
        log.warn("DB 제약 위반 발생: {}", e.getMessage());
        return BaseResponse.failure(GlobalErrorCode.CONFLICT);
    }

    // 그 외의 예외 처리 (예: NullPointerException, IllegalArgumentException 등)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception e) {
        log.error("예상치 못 한 서버 에러!!", e);
        return BaseResponse.failure(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    private String extractLeafField(Iterable<?> path) {
        String last = "parameter";
        for (Object node : path) {
            last = String.valueOf(node);
        }
        return last;
    }
}