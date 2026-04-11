package org.sopt.dto.response;

import org.sopt.exception.CustomException;

public class ApiResponse <T> {
    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    public ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data) {
        return new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, String message, T data) {
        return new ApiResponse<>(true, responseCode.getCode(), message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), data);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), null);
    }

    public static <T> ApiResponse<T> failure(ResponseCode responseCode) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> failure(ResponseCode responseCode, String message, T data) {
        return new ApiResponse<>(false, responseCode.getCode(), message, data);
    }

    public static <T> ApiResponse<T> failure(ResponseCode responseCode, String message) {
        return new ApiResponse<>(false, responseCode.getCode(), message, null);
    }

    public static <T> ApiResponse<T> failure(CustomException exception) {
        return new ApiResponse<>(false, exception.getCode(), exception.getMessage(), null);
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

}