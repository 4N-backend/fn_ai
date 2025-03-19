package com.fn.ai.delivery.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum DeliveryStatus {
  READY, DELIVERY, COMPLETED
  ;

  @JsonCreator
  public static DeliveryStatus parsing(String inputValue) {
    return Stream.of(DeliveryStatus.values())
        .filter(deliveryStatus -> deliveryStatus.toString().equals(inputValue.toUpperCase()))
        .findFirst()
        .orElse(null);
  }
}
