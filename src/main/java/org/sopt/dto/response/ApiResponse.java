package org.sopt.dto.response;

import org.sopt.exception.CustomException;

public class ApiResponse<T> {

    private final boolean success;
    private final String statusCode;
    private final String message;
    private final T data;

    public ApiResponse(boolean success, StatusCode statusCode, String message, T data) {
        this.success = success;
        this.statusCode = statusCode.getStatusCode();
        this.message = message;
        this.data = data;
    }

    // Success
    public static <T> ApiResponse<T> success(StatusCode statusCode, T data) {
        return new ApiResponse<>(true, statusCode, statusCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(StatusCode statusCode, String message, T data) {
        return  new ApiResponse<>(true, statusCode, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return  new ApiResponse<>(true, StatusCode.OK, StatusCode.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return  new ApiResponse<>(true, StatusCode.OK, message, null);
    }

    public static<T> ApiResponse<T> success(T data, String message) {
        return  new ApiResponse<>(true, StatusCode.OK, message, data);
    }

    public static ApiResponse<Void> success() {
        return  new ApiResponse<>(true, StatusCode.OK, StatusCode.OK.getMessage(), null);
    }

    // Failure
    public static <T> ApiResponse<T> failure(StatusCode statusCode) {
        return  new ApiResponse<>(false, statusCode, statusCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> failure(StatusCode statusCode, String message, T data) {
        return  new ApiResponse<>(false, statusCode, message, data);
    }

    public static <T> ApiResponse<T> failure(StatusCode statusCode, String message) {
        return  new ApiResponse<>(false, statusCode, message, null);
    }

    public static <T> ApiResponse<T> failure(CustomException exception) {
        return  new ApiResponse<>(false, exception.getStatusCode(), exception.getMessage(), null);
    }


    public boolean isSuccess() {
        return success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
