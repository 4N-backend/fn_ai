package com.fn.ai.user.model;

import com.fn.ai.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRespository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
