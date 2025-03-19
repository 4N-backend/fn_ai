package com.fn.ai.user.model;

import com.fn.ai.user.model.enums.DeliveryTypeEnum;
import com.fn.ai.user.model.vo.DeliverySequence;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_delivery_manager")
public class DeliveryManager {
    @Id
    private UUID id; // User id와 동일

    @Column
    private UUID hubId;

    @Enumerated(EnumType.STRING)
    private DeliveryTypeEnum type;

    @Embedded
    private DeliverySequence deliverySequence;

    @OneToOne
    @MapsId // DeliveryManager의 id가 User의 id와 동일하게 설정
    @JoinColumn(name = "user_id", nullable = false)
    @Setter(value = AccessLevel.NONE)
    private User user;
}
