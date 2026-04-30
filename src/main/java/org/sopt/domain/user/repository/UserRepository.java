package org.sopt.domain.user.repository;

import java.util.Optional;
import org.sopt.domain.user.entity.User;

public interface UserRepository {
    Optional<User> findById(Long id);

    User save(User user);
}
