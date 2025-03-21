package com.fn.ai.user.infrastructure.jpa;

import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.repository.DeliveryManagerRepository;
import com.fn.ai.user.model.type.DeliveryType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryManagerRepository extends DeliveryManagerRepository, JpaRepository<DeliveryManager, UUID>  {

    @Query("SELECT MAX(dm.deliverySequence) FROM DeliveryManager dm WHERE dm.type = :type AND dm.hubId IS NULL")
    Optional<Integer> findMaxSequenceByTypeAndHubIdIsNull(@Param("type") DeliveryType type);

    @Query("SELECT MAX(dm.deliverySequence) FROM DeliveryManager dm WHERE dm.type = :type AND dm.hubId = :hubId")
    Optional<Integer> findMaxSequenceByTypeAndHubId(@Param("type") DeliveryType type, @Param("hubId") UUID hubId);

    Optional<DeliveryManager> findByUserId(UUID userId);


}
