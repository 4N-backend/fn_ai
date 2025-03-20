package com.fn.ai.user.infrastructure.jpa;

import com.fn.ai.user.model.repository.UserRepository;
import com.fn.ai.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
