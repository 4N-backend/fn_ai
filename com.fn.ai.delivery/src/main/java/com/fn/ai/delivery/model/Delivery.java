package com.fn.ai.delivery.model;

import static com.fn.ai.delivery.model.type.DeliveryRouteStatus.DEPART_TO_HUB;
import static com.fn.ai.delivery.model.type.DeliveryStatus.COMPLETED;
import static com.fn.ai.delivery.model.type.DeliveryStatus.DELIVERY;
import static com.fn.ai.delivery.model.type.DeliveryStatus.READY;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.delivery.exception.AlreadyCompletedDelivery;
import com.fn.ai.delivery.exception.DeliverySequenceOutOfRangeException;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import com.fn.ai.delivery.model.vo.Address;
import com.fn.ai.delivery.model.vo.DeliverySequence;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "p_delivery")
public class Delivery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID orderId;

  @Column(nullable = false)
  private UUID departureHubId;

  @Column(nullable = false)
  private UUID arrivalHubId;

  @OneToMany(fetch = LAZY, cascade = PERSIST, mappedBy = "delivery")
  @OrderBy("sequence ASC")
  private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();

  @Embedded
  private Address targetAddress;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  @Builder
  public Delivery(
      UUID orderId,
      UUID departureHubId,
      UUID arrivalHubId,
      List<DeliveryRoute> deliveryRoutes,
      Address targetAddress,
      DeliveryStatus status
  ) {
    this.orderId = orderId;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.deliveryRoutes = deliveryRoutes;
    this.targetAddress = targetAddress;
    this.status = status;
  }

  public static Delivery createOf(
      UUID orderId,
      UUID departureHubId,
      UUID arrivalHubId,
      String targetAddress
  ) {
    return Delivery.builder()
        .orderId(orderId)
        .departureHubId(departureHubId)
        .arrivalHubId(arrivalHubId)
        .targetAddress(new Address(targetAddress))
        .deliveryRoutes(new ArrayList<>())
        .status(READY)
        .build();
  }

  public void addDeliveryRoute(DeliveryRoute route) {
    deliveryRoutes.add(route);
    route.setDelivery(this);
  }

  public void depart() {
    this.status = DELIVERY;
  }

  public void completed() {
    if (this.status == COMPLETED) {
      throw new AlreadyCompletedDelivery();
    }
    this.status = COMPLETED;
  }

  public void departFromHub(int sequence) {
    DeliveryRoute currentRoute = getRouteBySequence(new DeliverySequence(sequence));
    if (currentRoute.isFirst()) {
      this.depart();
    }
    currentRoute.updateStatue(DEPART_TO_HUB);
  }

  public void arriveToHub(int sequence, double distance, long duration) {
    DeliveryRoute currentRoute = getRouteBySequence(new DeliverySequence(sequence));
    currentRoute.arrived(distance, duration);
  }

  public DeliveryRoute getRouteBySequence(DeliverySequence sequence) {
    if (sequence.getValue() > deliveryRoutes.size()) {
      throw new DeliverySequenceOutOfRangeException();
    }
    return deliveryRoutes.get(sequence.getValue());
  }
}
