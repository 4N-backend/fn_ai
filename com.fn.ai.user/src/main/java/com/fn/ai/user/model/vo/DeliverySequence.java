package com.fn.ai.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
@Getter
public class DeliverySequence {

    @Column(name = "delivery_sequence", nullable = false)
    private int value;

    public DeliverySequence(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        // 음수가 들어오면 예외 발생
        if (value <= 0) {
            throw new IllegalArgumentException("DeliverySequence must be a non-negative integer.");
        }
    }

    public DeliverySequence update(int value) {
        return new DeliverySequence(value);
    }
}
