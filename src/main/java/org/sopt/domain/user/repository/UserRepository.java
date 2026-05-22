package org.sopt.domain.user.repository;

import java.util.Optional;
import org.sopt.domain.user.entity.User;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
