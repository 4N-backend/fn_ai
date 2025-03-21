package com.fn.ai.company.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Embeddable
@NoArgsConstructor
public class Name {

    @Column(name ="name")
    private String value;

    public Name(String value){
        validate(value);
        this.value =value;
    }

    private void validate(String value) {

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("이름은 Null일 수 없습니다.");
        }
    }

    public Name update(String value){
        return new Name(value);
    }

}
