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
  private int value;

  public DeliverySequence(int value) {
    validate(value);
    this.value = value;
  }

  private void validate(int value) {
    if (value < 0) {
      throw new IllegalArgumentException("Wrong sequence value");
    }
  }

  public DeliverySequence update(int value) {
    return new DeliverySequence(value);
  }

  public boolean isFirst() {
    return this.value == 0;
  }
}
