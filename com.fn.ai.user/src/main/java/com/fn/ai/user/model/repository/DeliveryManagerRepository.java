package com.fn.ai.user.model.repository;

import com.fn.ai.user.model.DeliveryManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID>  {
}
