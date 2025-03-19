package com.fn.ai.delivery.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Embeddable
public class DeliverySequence {

  @Column(name = "sequence", nullable = false)
  private long value;

  public DeliverySequence(long value) {
    validate(value);
    this.value = value;
  }

  private void validate(long value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Wrong sequence value");
    }
  }

  public DeliverySequence update(long value) {
    return new DeliverySequence(value);
  }
}
