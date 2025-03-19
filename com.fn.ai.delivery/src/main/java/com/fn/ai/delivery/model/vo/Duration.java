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
public class Duration {

  @Column(name = "duration", nullable = false)
  private long value;

  public Duration(long value) {
    validate(value);
    this.value = value;
  }

  private void validate(long value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Wrong duration value");
    }
  }

  public Duration update(long value) {
    return new Duration(value);
  }
}