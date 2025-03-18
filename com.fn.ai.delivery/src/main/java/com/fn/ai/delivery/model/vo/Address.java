package com.fn.ai.delivery.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
@Getter
public class Address {

  @Column(name = "target_address", nullable = false)
  private String value;

  public Address(String value) {
    validate(value);
    this.value = value;
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new NullPointerException("Address is Null");
    }
  }

  public Address update(String value) {
    return new Address(value);
  }
}