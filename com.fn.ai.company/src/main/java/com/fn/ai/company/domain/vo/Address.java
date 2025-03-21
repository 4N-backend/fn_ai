package com.fn.ai.company.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Getter
public class Address {

    @Column(name = "address", nullable = false)
    private String value;

    public Address(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("주소는 Null일 수 없습니다.");
        }
    }

    public Address update(String value){
        return new Address(value);
    }
}
