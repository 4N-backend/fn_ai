package com.fn.ai.user.model.repository;

import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.type.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID> {

    boolean existsByUserId(UUID userId);

    long countByType(DeliveryType type); // 배송 타입별 담당자 수 조회

    long countByTypeAndHubId(DeliveryType type, UUID hubId); // 특정 허브 내 배송 담당자 수 조회

    Optional<Integer> findMaxSequenceByTypeAndHubId(DeliveryType type, UUID hubId);// 허브 내 가장 큰 순번 찾기

    Optional<Integer> findMaxSequenceByTypeAndHubIdIsNull(DeliveryType type); // 허브 없는 배송 담당자 가장 큰 순번 찾기
}
