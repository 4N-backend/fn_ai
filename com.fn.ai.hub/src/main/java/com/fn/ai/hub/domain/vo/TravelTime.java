package com.fn.ai.hub.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Embeddable
@NoArgsConstructor
public class TravelTime {

    @Column(name = "travel_time", nullable = false)
    private long value;

    public TravelTime(long value){
        validate(value);
        this.value = value;
    }

    private void validate(long value){
        if (value <= 0) {
            throw new IllegalArgumentException("너무 적은 값입니다.");
        }
    }

    public TravelTime update(long value){
        return new TravelTime(value);
    }
}
