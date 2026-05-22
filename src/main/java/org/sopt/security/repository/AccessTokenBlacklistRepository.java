package org.sopt.security.repository;

import org.sopt.security.AccessTokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenBlacklistRepository extends JpaRepository<AccessTokenBlacklist, Long> {
    boolean existsByToken(String token);
}
