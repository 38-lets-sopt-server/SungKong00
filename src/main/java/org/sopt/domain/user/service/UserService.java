package org.sopt.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.request.SignUpRequest;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.exception.UserErrorCode;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.CustomException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        // 내부 로직용 회원 조회
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND, "유저를 찾을 수 없습니다. id: " + id));
    }

    @Transactional
    public UserResponse signUp(SignUpRequest request) {
        // 회원가입 중복 확인
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(UserErrorCode.USER_ALREADY_EXISTS, "이미 사용중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.nickname())) {
            throw new CustomException(UserErrorCode.USER_ALREADY_EXISTS, "이미 사용중인 닉네임입니다.");
        }

        // 비밀번호는 BCrypt로 저장
        User user = new User(request.nickname(), request.email(), passwordEncoder.encode(request.password()));

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getNickname(), user.getEmail());
    }
}
