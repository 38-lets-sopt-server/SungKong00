package org.sopt.domain.user.repository.jpa;

import org.sopt.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {
}
