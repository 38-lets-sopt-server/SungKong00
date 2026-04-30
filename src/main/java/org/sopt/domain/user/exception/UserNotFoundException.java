package org.sopt.domain.user.exception;

import org.sopt.global.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(UserErrorCode.USER_NOT_FOUND, message);
    }

    public UserNotFoundException(Long id) {
        super(UserErrorCode.USER_NOT_FOUND, "🚫 유저를 찾을 수 없습니다. id: " + id);
    }
}
