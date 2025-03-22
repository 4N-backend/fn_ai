package com.fn.ai.delivery.presentation.external.dto;

import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.DeliveryRoute;
import com.fn.ai.delivery.model.type.DeliveryStatus;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryDetailsResponseDto(UUID deliveryId, UUID orderId, UUID departureHubId,
                                         UUID arrivalHubId, String targetAddress,
                                         DeliveryStatus status,
                                         List<DeliveryRouteResponseDto> routes) {

  @Builder
  public record DeliveryRouteResponseDto(UUID deliveryRouteId, UUID departureHubId,
                                         UUID arrivalHubId, UUID deliveryManagerId,
                                         Double estimatedDistance, Long estimatedDuration,
                                         Double actualDistance, Long actualDuration,
                                         Integer sequence) {

    public static DeliveryRouteResponseDto fromEntity(DeliveryRoute deliveryRoute) {
      return DeliveryRouteResponseDto.builder().deliveryRouteId(deliveryRoute.getId())
          .departureHubId(deliveryRoute.getDepartureHubId())
          .arrivalHubId(deliveryRoute.getArrivalHubId())
          .deliveryManagerId(deliveryRoute.getDeliveryManagerId()).estimatedDistance(
              deliveryRoute.getEstimatedDistance() != null ? deliveryRoute.getEstimatedDistance()
                  .getValue() : null).estimatedDuration(
              deliveryRoute.getEstimatedDuration() != null ? deliveryRoute.getEstimatedDuration()
                  .getValue() : null).actualDistance(
              deliveryRoute.getActualDistance() != null ? deliveryRoute.getActualDistance()
                  .getValue() : null).actualDuration(
              deliveryRoute.getActualDuration() != null ? deliveryRoute.getActualDuration()
                  .getValue() : null).sequence(
              deliveryRoute.getSequence() != null ? deliveryRoute.getSequence().getValue() : null)
          .build();
    }
  }


  public static DeliveryDetailsResponseDto fromEntity(Delivery delivery) {
    return DeliveryDetailsResponseDto.builder().deliveryId(delivery.getId())
        .orderId(delivery.getOrderId()).departureHubId(delivery.getDepartureHubId())
        .arrivalHubId(delivery.getArrivalHubId())
        .targetAddress(delivery.getTargetAddress().getValue()).status(delivery.getStatus()).routes(
            delivery.getDeliveryRoutes().stream().map(DeliveryRouteResponseDto::fromEntity)
                .toList()).build();
  }
}
