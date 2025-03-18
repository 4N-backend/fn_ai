package com.fn.ai.delivery.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Distance {

  @Column(name = "distance", nullable = false)
  private double value;

  public Distance(double value) {
    validate(value);
    this.value = value;
  }

  private void validate(double value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Wrong distance value");
    }
  }

  public Distance update(double value) {
    return new Distance(value);
  }
}