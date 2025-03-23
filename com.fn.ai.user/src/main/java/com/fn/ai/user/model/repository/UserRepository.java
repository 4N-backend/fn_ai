package com.fn.ai.user.model.repository;

import com.fn.ai.user.model.User;
import com.fn.ai.user.model.vo.Username;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(Username username);
}

