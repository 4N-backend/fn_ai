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
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NullPointerException("Password is Null");
        }
    }

    public Password update(String value) {
        return new Password(value);
    }
}
