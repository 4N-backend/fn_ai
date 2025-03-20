package com.fn.ai.delivery.model;

import static com.fn.ai.delivery.model.type.DeliveryRouteStatus.ARRIVED_HUB;
import static com.fn.ai.delivery.model.type.DeliveryRouteStatus.READY;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.delivery.model.type.DeliveryRouteStatus;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class DeliveryRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

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

  @Column(nullable = false)
  private DeliveryRouteStatus status;

  @Builder
  private DeliveryRoute(
      Delivery delivery,
      UUID departureHubId,
      UUID arrivalHubId,
      UUID deliveryManagerId,
      Distance estimatedDistance,
      Duration estimatedDuration,
      Distance actualDistance,
      Duration actualDuration,
      DeliverySequence sequence,
      DeliveryRouteStatus status
  ) {
    this.delivery = delivery;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.deliveryManagerId = deliveryManagerId;
    this.estimatedDistance = estimatedDistance;
    this.estimatedDuration = estimatedDuration;
    this.actualDistance = actualDistance;
    this.actualDuration = actualDuration;
    this.sequence = sequence;
    this.status = status;
  }

  public static DeliveryRoute of(
      UUID departureHubId,
      UUID arrivalHubId,
      Double estimatedDistance,
      Long estimatedDuration,
      Integer sequence
  ) {
    return DeliveryRoute.builder()
        .departureHubId(departureHubId)
        .arrivalHubId(arrivalHubId)
        .estimatedDistance(new Distance(estimatedDistance))
        .estimatedDuration(new Duration(estimatedDuration))
        .sequence(new DeliverySequence(sequence))
        .status(READY)
        .build();
  }

  public void updateStatue(DeliveryRouteStatus status) {
    this.status = status;
  }

  public boolean isFirst() {
    return this.sequence.isFirst();
  }

  public void arrived(double distance, long duration) {
    updateStatue(ARRIVED_HUB);
    updateActualRecord(distance, duration);
  }

  private void updateActualRecord(double distance, long duration) {
    this.getActualDistance().update(distance);
    this.getActualDuration().update(duration);
  }

  protected void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }
}
