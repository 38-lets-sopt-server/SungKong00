package org.sopt.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.user.dto.request.SignUpRequest;
import org.sopt.domain.user.dto.response.UserResponse;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.user.exception.UserNotFoundException;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponse signUp(SignUpRequest request) {
        User user = new User(request.nickname(), request.email());

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getNickname(), user.getEmail());
    }
}
