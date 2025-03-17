package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.infrastructure.jpa.HubJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HubRepositoryImpl implements HubRepository {

    private final HubJpaRepository jpaRepository;


    @Override
    public Optional<Hub> findByName(String name) {
        return null;
    }
}
