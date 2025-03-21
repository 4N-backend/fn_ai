package com.fn.ai.user.application.service;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.user.infrastructure.jpa.JpaDeliveryManagerRepository;
import com.fn.ai.user.model.DeliveryManager;
import com.fn.ai.user.model.User;
import com.fn.ai.user.model.repository.UserRepository;
import com.fn.ai.user.model.type.DeliveryType;
import com.fn.ai.user.presentation.dto.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional(readOnly = true)
    @Override
    public DeliveryManagerResponseDto getDeliveryManager(UUID userId) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("배송 담당자 정보를 찾을 수 없습니다: " + userId));

        return DeliveryManagerResponseDto.from(deliveryManager);
    }

    @Transactional(readOnly = true)
    @Override
    public UUID getHubIdOf(UUID userId) {
        return deliveryManagerRepository.findByUserId(userId)
                .map(DeliveryManager::getHubId)
                .orElseThrow(() -> new EntityNotFoundException("배송 담당자 정보를 찾을 수 없습니다: " + userId));
    }

    @Override
    public Page<DeliveryManagerInfoResponseDto> getAllDeliveryManagers(UserRoleEnum role, UUID requesterId,
                                                                       int page, int size, String sortBy, boolean isAsc) {
        Sort sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (role == UserRoleEnum.MASTER) {
            return deliveryManagerRepository.findAll(pageable)
                    .map(DeliveryManagerInfoResponseDto::of);
        }

//        if (role == UserRoleEnum.DELIVERY_MANAGER) {
//            return deliveryManagerRepository.findByUserId(requesterId)
//                    .map(dm -> new PageImpl<>(List.of(DeliveryManagerInfoResponseDto.of(dm)), pageable, 1))
//                    .orElseThrow(() -> new EntityNotFoundException("배송 담당자 정보를 찾을 수 없습니다: " + requesterId));
//        }

        if (role == UserRoleEnum.HUB_MANAGER) {
            UUID hubId = getHubIdOf(requesterId);
            return deliveryManagerRepository.findAllByHubId(hubId, pageable)
                    .map(DeliveryManagerInfoResponseDto::of);
        }

        throw new IllegalStateException("권한이 없습니다.");
    }

    @Override
    @Transactional
    public DeliveryManagerResponseDto updateDeliveryManager(UserRoleEnum role, UUID requesterId, UUID deliveryManagerId, DeliveryManagerUpdaterRequestDto requestDto) {
        try {

            DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliveryManagerId)
                    .orElseThrow(() -> new EntityNotFoundException("배송 담당자를 찾을 수 없습니다: " + deliveryManagerId));

            // 권한 체크
            if (role != UserRoleEnum.MASTER) {
                if (role == UserRoleEnum.HUB_MANAGER) {
                    UUID requesterHubId = getHubIdOf(requesterId);
                    UUID targetHubId = getHubIdOf(deliveryManagerId);
                    if (!Objects.equals(requesterHubId, targetHubId)) {
                        throw new IllegalStateException("허브 관리자는 본인의 허브 배송 담당자만 수정할 수 있습니다.");
                    }
                } else {
                    throw new IllegalStateException("수정 권한이 없습니다.");
                }
            }

            DeliveryType type;
            try {
                type = DeliveryType.valueOf(requestDto.type().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("잘못된 배송 담당자 유형입니다: " + requestDto.type());
            }

            // hubId 처리
            UUID hubId = requestDto.hubId();
            if (type == DeliveryType.HUB_DELIVERY_MANAGER) {
                hubId = null;
            } else if (type == DeliveryType.COMPANY_DELIVERY_MANAGER && hubId == null) {
                throw new IllegalStateException("COMPANY_DELIVERY_MANAGER는 hubId가 필요합니다.");
            }

            deliveryManager.updateInfo(type, hubId);

            return DeliveryManagerResponseDto.from(deliveryManager);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("배송 담당자 수정 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public DeliveryManagerDeleteResponseDto deleteDeliveryManager(UserRoleEnum role, UUID requesterId, UUID deliveryManagerId) {

        DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliveryManagerId)
                .orElseThrow(() -> new EntityNotFoundException("배송 담당자를 찾을 수 없습니다: " + deliveryManagerId));

        if (role != UserRoleEnum.MASTER) {
            if (role == UserRoleEnum.HUB_MANAGER) {
                UUID requesterHubId = getHubIdOf(requesterId);
                UUID targetHubId = getHubIdOf(deliveryManagerId);

                if (!Objects.equals(requesterHubId, targetHubId)) {
                    throw new IllegalStateException("허브 관리자는 본인 허브의 배송 담당자만 삭제할 수 있습니다.");
                }
            } else {
                throw new IllegalStateException("삭제 권한이 없습니다.");
            }
        }
        // 소프트 삭제
        deliveryManager.delete();
        return new DeliveryManagerDeleteResponseDto(deliveryManagerId);
    }



    private int calculateNextSequence(DeliveryType type, UUID hubId) {
        Optional<Integer> maxSeq = (hubId == null)
                ? deliveryManagerRepository.findMaxSequenceByTypeAndHubIdIsNull(type)
                : deliveryManagerRepository.findMaxSequenceByTypeAndHubId(type, hubId);

        return maxSeq.orElse(0) + 1;
    }
}
