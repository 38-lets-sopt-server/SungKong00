package org.sopt.global.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.global.exception.CustomException;
import org.springframework.http.ResponseEntity;

@Schema(description = "API 공통 응답 포맷")
public class BaseResponse<T> {

    @Schema(description = "HTTP 상태 코드 (예: 200, 201, 400)", example = "200")
    private final int status;

    @Schema(description = "커스텀 응답 코드 (예: SUCCESS_200, GLOBAL_BAD_REQUEST)", example = "SUCCESS_200")
    private final String code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private final String message;

    @Schema(description = "응답 데이터 (데이터가 없을 경우 null 반환)")
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

    // 데이터가 없는 성공
    public static <T> ResponseEntity<BaseResponse<T>> success(BaseSuccessCode successCode) {
        return success(successCode, null);
    }

    // --- Failure Factory Methods ---

    // BaseErrorCode을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<BaseResponse<T>> failure(BaseErrorCode errorCode) {
        BaseResponse<T> response = new BaseResponse<>(errorCode, null);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // BaseErrorCode을 이용해서 실패 응답 + 데이터
    public static <T> ResponseEntity<BaseResponse<T>> failure(BaseErrorCode errorCode, T data) {
        BaseResponse<T> response = new BaseResponse<>(errorCode, data);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // CustomException을 이용해서 실패 응답을 만들 때
    public static <T> ResponseEntity<BaseResponse<T>> failure(CustomException exception) {
        BaseErrorCode errorCode = exception.getErrorCode();
        BaseResponse<T> response = new BaseResponse<>(errorCode, exception.getMessage(), null);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    // Getters
    public int getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}