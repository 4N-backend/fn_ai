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
public class Location {

    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;

    public Location(double latitude,double longitude){
        validate(latitude,longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void validate(double latitude,double longitude){
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees. Given: " + latitude);
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees. Given: " + longitude);
        }
    }

    public Location update(double latitude,double longitude){
        return new Location(latitude, longitude);
    }
}
