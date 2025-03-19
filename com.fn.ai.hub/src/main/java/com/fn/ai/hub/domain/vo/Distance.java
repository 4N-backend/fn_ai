package com.fn.ai.hub.domain.vo;

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

    public Distance(double departure,double arrival){
        validate(value);
        validate(arrival);

        /**
         * TODO 거리 계산 로직 짤 것
         * 밑에는 임시
         */
        this .value = arrival-departure;
    }

    private void validate(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Wrong distance value");
        }
    }

    public Distance update(double departure,double arrival){
        return new Distance(departure,arrival);
    }
}
