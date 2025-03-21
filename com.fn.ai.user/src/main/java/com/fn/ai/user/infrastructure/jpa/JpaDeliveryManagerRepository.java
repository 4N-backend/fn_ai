package com.fn.ai.user.infrastructure.jpa;

import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.repository.DeliveryManagerRepository;
import com.fn.ai.user.model.type.DeliveryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID> {

    @EntityGraph(attributePaths = {"user"})
    Page<DeliveryManager> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<DeliveryManager> findAllByHubId(UUID hubId, Pageable pageable);

    Optional<DeliveryManager> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    Optional<Integer> findMaxSequenceByTypeAndHubIdIsNull(DeliveryType type);

    Optional<Integer> findMaxSequenceByTypeAndHubId(DeliveryType type, UUID hubId);

    long countByType(DeliveryType type);

    long countByTypeAndHubId(DeliveryType type, UUID hubId);
}

