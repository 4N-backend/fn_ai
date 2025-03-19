package com.fn.ai.user.model;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, UUID> {

  Optional<User> findByUsername(String username);
}
