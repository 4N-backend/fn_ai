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
public class Username {

    @Column(name = "username", nullable = false)
    private String value;

    public Username(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NullPointerException("Username is Null");
        }
        // 정규식: 4자 이상 10자 이하이며, 소문자와 숫자로만 구성되어야 함
        if (!value.matches("^[a-z0-9]{4,10}$")) {
            throw new IllegalArgumentException("Username must be 4-10 characters long and contain only lowercase letters and numbers.");
        }
    }
}