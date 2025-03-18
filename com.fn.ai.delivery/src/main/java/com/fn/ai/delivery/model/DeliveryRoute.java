package com.fn.ai.delivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_delivery_route")
public class DeliveryRoute {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID deliveryId;

  @Column(nullable = false)
  private UUID departureHubId;

  @Column(nullable = false)
  private UUID arrivalHubId;

  private UUID deliveryManagerId;

  @Column(nullable = false)
  private BigDecimal estimatedDistance;

  @Column(nullable = false)
  private Duration estimatedDuration;

  private BigDecimal actualDistance;

  private Duration actualDuration;

  @Column(nullable = false)
  private int sequence;

  @Builder
  private DeliveryRoute(UUID deliveryId, UUID departureHubId, UUID arrivalHubId, UUID deliveryManagerId,
      BigDecimal estimatedDistance, Duration estimatedDuration, BigDecimal actualDistance,
      Duration actualDuration, int sequence) {
    this.deliveryId = deliveryId;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.deliveryManagerId = deliveryManagerId;
    this.estimatedDistance = estimatedDistance;
    this.estimatedDuration = estimatedDuration;
    this.actualDistance = actualDistance;
    this.actualDuration = actualDuration;
    this.sequence = sequence;
  }

  public static DeliveryRoute of(UUID deliveryId, UUID departureHubId, UUID arrivalHubId,
      BigDecimal estimatedDistance, Duration estimatedDuration) {
    return DeliveryRoute.builder()
        .deliveryId(deliveryId)
        .departureHubId(departureHubId)
        .arrivalHubId(arrivalHubId)
        .estimatedDistance(estimatedDistance)
        .estimatedDuration(estimatedDuration)
        .build();
  }
}
