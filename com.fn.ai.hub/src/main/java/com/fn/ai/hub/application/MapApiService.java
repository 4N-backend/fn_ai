package com.fn.ai.hub.application;

import com.fn.ai.hub.domain.vo.Location;
import org.springframework.http.ResponseEntity;

public interface MapApiService {
    String createUri(Location departureLocation, Location arrivalLocation);

    /**
     * double[0] : distance
     * double[1] : travelTime
     * @param uri
     * @return double[]
     */
    long[] RequestApi(String uri);
}
