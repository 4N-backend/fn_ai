package com.fn.ai.user.model;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.user.model.type.DeliveryType;
import com.fn.ai.user.presentation.dto.DeliveryManagerRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Table(name = "p_delivery_manager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeliveryManager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_manager_id")
    private UUID id;

    @Column(nullable = true)
    private UUID hubId;

    @Enumerated(EnumType.STRING)
    private DeliveryType type;

    @Column(name = "delivery_sequence", nullable = false)
    private int deliverySequence;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public static DeliveryManager of(DeliveryManagerRequestDto requestDto, int sequence, User user) {
        DeliveryManager manager = DeliveryManager.builder()
                .hubId(requestDto.hubId())
                .type(DeliveryType.valueOf(requestDto.type().toUpperCase()))
                .deliverySequence(sequence)
                .user(user)
                .build();
        user.assignDeliveryManager(manager);
        return manager;
    }

    public void updateInfo(DeliveryType type, UUID hubId) {
        this.type = type;
        this.hubId = hubId;
    }


}
