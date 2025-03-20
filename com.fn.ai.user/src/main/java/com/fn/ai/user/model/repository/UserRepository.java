package com.fn.ai.user.model.repository;

import com.fn.ai.user.model.User;
import com.fn.ai.user.model.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(Username username);
}

