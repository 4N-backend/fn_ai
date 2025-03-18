package com.fn.ai.delivery.model;

import static com.fn.ai.delivery.model.type.DeliveryStatus.*;

import com.fn.ai.delivery.model.type.DeliveryStatus;
import jakarta.persistence.Column;
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
@Table(name = "p_delivery")
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID orderId;

  @Column(nullable = false)
  private UUID departureHubId;

  @Column(nullable = false)
  private UUID arrivalHubId;

  @Column(nullable = false)
  private String targetAddress;

  @Column(nullable = false)
  private DeliveryStatus status;

  @Builder
  private Delivery(
      UUID orderId,
      UUID departureHubId,
      UUID arrivalHubId,
      String targetAddress,
      DeliveryStatus status
  ) {
    this.orderId = orderId;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.targetAddress = targetAddress;
    this.status = status;
  }

  public static Delivery of(
      UUID orderId,
      UUID departureHubId,
      UUID arrivalHubId,
      String targetAddress
  ) {
    return Delivery.builder()
        .orderId(orderId)
        .departureHubId(departureHubId)
        .arrivalHubId(arrivalHubId)
        .targetAddress(targetAddress)
        .status(READY)
        .build();
  }
}
