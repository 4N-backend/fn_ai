package com.fn.ai.delivery.model;

import com.fn.ai.delivery.model.vo.DeliverySequence;
import com.fn.ai.delivery.model.vo.Distance;
import com.fn.ai.delivery.model.vo.Duration;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(
          name = "value", column = @Column(name = "estimated_distance", nullable = false))
  })
  private Distance estimatedDistance;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(
          name = "value", column = @Column(name = "estimated_duration", nullable = false))
  })
  private Duration estimatedDuration;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(
          name = "value", column = @Column(name = "actual_distance"))
  })
  private Distance actualDistance;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(
          name = "value", column = @Column(name = "actual_duration"))
  })
  private Duration actualDuration;

  @Embedded
  private DeliverySequence sequence;

  @Builder
  private DeliveryRoute(
      UUID deliveryId,
      UUID departureHubId,
      UUID arrivalHubId,
      UUID deliveryManagerId,
      Distance estimatedDistance,
      Duration estimatedDuration,
      Distance actualDistance,
      Duration actualDuration,
      DeliverySequence sequence
  ) {
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

  public static DeliveryRoute of(
      UUID deliveryId,
      UUID departureHubId,
      UUID arrivalHubId,
      Double estimatedDistance,
      Long estimatedDuration,
      Long sequence
  ) {
    return DeliveryRoute.builder()
        .deliveryId(deliveryId)
        .departureHubId(departureHubId)
        .arrivalHubId(arrivalHubId)
        .estimatedDistance(new Distance(estimatedDistance))
        .estimatedDuration(new Duration(estimatedDuration))
        .sequence(new DeliverySequence(sequence))
        .build();
  }
}
