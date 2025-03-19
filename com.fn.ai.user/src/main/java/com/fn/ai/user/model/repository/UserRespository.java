package com.fn.ai.user.model.repository;

import com.fn.ai.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRespository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername_Value(String username);

}

