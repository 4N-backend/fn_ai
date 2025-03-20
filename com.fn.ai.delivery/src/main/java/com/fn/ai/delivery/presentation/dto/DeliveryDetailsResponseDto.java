package com.fn.ai.delivery.presentation.dto;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.DeliveryRoute;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import com.fn.ai.delivery.model.vo.DeliverySequence;
import com.fn.ai.delivery.model.vo.Distance;
import com.fn.ai.delivery.model.vo.Duration;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryDetailsResponseDto(
    UUID deliveryId,
    UUID orderId,
    UUID departureHubId,
    UUID arrivalHubId,
    String targetAddress,
    DeliveryStatus status,
    List<DeliveryRouteResponseDto> routes
) {

  @Builder
  public record DeliveryRouteResponseDto(
      UUID deliveryRouteId,
      UUID departureHubId,
      UUID arrivalHubId,
      UUID deliveryManagerId,
      Distance estimatedDistance,
      Duration estimatedDuration,
      Distance actualDistance,
      Duration actualDuration,
      DeliverySequence sequence
  ) {
    public static DeliveryRouteResponseDto fromEntity(DeliveryRoute deliveryRoute) {
      return DeliveryRouteResponseDto.builder()
          .deliveryRouteId(deliveryRoute.getId())
          .departureHubId(deliveryRoute.getDepartureHubId())
          .arrivalHubId(deliveryRoute.getArrivalHubId())
          .deliveryManagerId(deliveryRoute.getDeliveryManagerId())
          .estimatedDistance(deliveryRoute.getEstimatedDistance())
          .estimatedDuration(deliveryRoute.getEstimatedDuration())
          .actualDistance(deliveryRoute.getActualDistance())
          .actualDuration(deliveryRoute.getActualDuration())
          .sequence(deliveryRoute.getSequence())
          .build();
    }
  }


  public static DeliveryDetailsResponseDto fromEntity(Delivery delivery) {
    return DeliveryDetailsResponseDto.builder()
        .deliveryId(delivery.getId())
        .orderId(delivery.getOrderId())
        .departureHubId(delivery.getDepartureHubId())
        .arrivalHubId(delivery.getArrivalHubId())
        .targetAddress(delivery.getTargetAddress().getValue())
        .status(delivery.getStatus())
        .routes(delivery.getDeliveryRoutes().stream()
            .map(DeliveryRouteResponseDto::fromEntity).toList())
        .build();
  }
}
