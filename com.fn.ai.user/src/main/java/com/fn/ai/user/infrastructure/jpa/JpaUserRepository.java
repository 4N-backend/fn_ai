package com.fn.ai.user.infrastructure.jpa;

import com.fn.ai.user.model.User;
import com.fn.ai.user.model.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
