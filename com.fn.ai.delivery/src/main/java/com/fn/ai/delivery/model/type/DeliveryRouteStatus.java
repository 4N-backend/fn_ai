package com.fn.ai.delivery.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum DeliveryRouteStatus {
  READY, DEPART_TO_HUB, ARRIVED_HUB, DEPART_TO_TARGET_ADDRESS;

  @JsonCreator
  public static DeliveryRouteStatus parsing(String inputValue) {
    return Stream.of(DeliveryRouteStatus.values())
        .filter(
            deliveryRouteStatus -> deliveryRouteStatus.toString().equals(inputValue.toUpperCase()))
        .findFirst()
        .orElse(null);
  }
}
