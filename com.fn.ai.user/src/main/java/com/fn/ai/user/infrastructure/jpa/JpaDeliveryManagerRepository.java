package com.fn.ai.user.infrastructure.jpa;

import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.type.DeliveryType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaDeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID> {

    @EntityGraph(attributePaths = {"user"})
    Page<DeliveryManager> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<DeliveryManager> findAllByHubId(UUID hubId, Pageable pageable);

    Optional<DeliveryManager> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    @Query("SELECT MAX(dm.deliverySequence) FROM DeliveryManager dm "
        + "WHERE dm.type = :type AND dm.hubId IS NULL")
    Optional<Integer> findMaxSequenceByTypeAndHubIdIsNull(@Param("type") DeliveryType type);

    @Query("SELECT MAX(dm.deliverySequence) FROM DeliveryManager dm "
        + "WHERE dm.type = :type AND dm.hubId = :hubId")
    Optional<Integer> findMaxSequenceByTypeAndHubId(@Param("type") DeliveryType type, @Param("hubId") UUID hubId);

    @Query("SELECT dm FROM DeliveryManager dm "
        + "WHERE (dm.deliverySequence >= :sequence AND dm.hubId IS NULL) "
        + "ORDER BY dm.deliverySequence ASC")
    List<DeliveryManager> findAllByNextSequenceAndHubIsNull(@Param("sequence") long sequence);

    long countByType(DeliveryType type);

    long countByTypeAndHubId(DeliveryType type, UUID hubId);
}

