package com.fn.ai.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Getter
public class Name {

    @Column(name = "name",nullable = false)
    private String value;

    public Name(String value){
        validate(value);
        this.value = value;
    }

    private void validate(String value){
        if(value == null || value.isBlank()){
            throw new NullPointerException("Name is Null");
        }
    }

    public Name update(String value){
        return new Name(value);
    }
}
