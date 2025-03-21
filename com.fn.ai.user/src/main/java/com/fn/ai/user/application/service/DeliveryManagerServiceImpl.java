package com.fn.ai.user.application.service;

import com.fn.ai.user.infrastructure.jpa.JpaDeliveryManagerRepository;
import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.User;
import com.fn.ai.user.model.repository.UserRepository;
import com.fn.ai.user.model.type.DeliveryType;
import com.fn.ai.user.presentation.dto.DeliveryManagerRequestDto;
import com.fn.ai.user.presentation.dto.DeliveryManagerResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

    private final UserRepository userRepository;
    private final JpaDeliveryManagerRepository deliveryManagerRepository;

    @Transactional
    @Override
    public DeliveryManagerResponseDto createDeliveryManager(UUID userId, DeliveryManagerRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        // 배송 담당자 등록 여부
        if (deliveryManagerRepository.existsByUserId(userId)) {
            throw new IllegalStateException("해당 사용자는 이미 배송 담당자로 등록되어 있습니다.");
        }

        DeliveryType deliveryType;
        try {
            deliveryType = DeliveryType.valueOf(requestDto.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("잘못된 배송 담당자 유형입니다: " + requestDto.type());
        }

        UUID hubId = requestDto.hubId();

        // 허브별 최대 인원 제한 확인
        if (deliveryType == DeliveryType.HUB_DELIVERY_MANAGER) {
            long hubDeliveryCount = deliveryManagerRepository.countByType(DeliveryType.HUB_DELIVERY_MANAGER);
            if (hubDeliveryCount >= 10) {
                throw new IllegalStateException("허브 배송 담당자는 최대 10명까지 등록 가능합니다.");
            }
            hubId = null;
        } else if (deliveryType == DeliveryType.COMPANY_DELIVERY_MANAGER) {
            if (hubId == null) {
                throw new IllegalStateException("업체 배송 담당자는 hubId가 필요합니다.");
            }
            long companyDeliveryCount = deliveryManagerRepository.countByTypeAndHubId(DeliveryType.COMPANY_DELIVERY_MANAGER, hubId);
            if (companyDeliveryCount >= 10) {
                throw new IllegalStateException("해당 허브에는 최대 10명의 업체 배송 담당자만 등록 가능합니다.");
            }
        }

        // 배송 순번 계산
        int newSequence = calculateNextSequence(deliveryType, hubId);

        DeliveryManager deliveryManager = DeliveryManager.of(requestDto, newSequence, user);
        deliveryManagerRepository.save(deliveryManager);

        return DeliveryManagerResponseDto.from(deliveryManager);
    }

    private int calculateNextSequence(DeliveryType type, UUID hubId) {
        Optional<Integer> maxSeq = (hubId == null)
                ? deliveryManagerRepository.findMaxSequenceByTypeAndHubIdIsNull(type)
                : deliveryManagerRepository.findMaxSequenceByTypeAndHubId(type, hubId);

        return maxSeq.orElse(0) + 1;
    }
}
