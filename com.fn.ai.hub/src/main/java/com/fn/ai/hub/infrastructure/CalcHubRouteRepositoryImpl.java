package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.CalcHubRouteDistance;
import com.fn.ai.hub.domain.repository.CalcHubRouteRepository;
import com.fn.ai.hub.infrastructure.jpa.CalcHubRouteJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalcHubRouteRepositoryImpl implements CalcHubRouteRepository {

    private final CalcHubRouteJpaRepository jpaRepository;

    @Override
    public CalcHubRouteDistance save(CalcHubRouteDistance calculateHubDistance) {
        return jpaRepository.save(calculateHubDistance);
    }

    @Override
    public List<CalcHubRouteDistance> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void saveAll(List<CalcHubRouteDistance> resultList) {
        jpaRepository.saveAll(resultList);
    }
}
