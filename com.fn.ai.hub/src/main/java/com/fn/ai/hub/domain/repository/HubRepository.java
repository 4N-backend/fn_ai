package com.fn.ai.hub.domain.repository;

import com.fn.ai.hub.domain.Hub;
import java.util.Optional;

public interface HubRepository {
    Optional<Hub> findByName(String name);
}
