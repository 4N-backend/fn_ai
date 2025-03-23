package com.fn.ai.hub.domain.repository;

import com.fn.ai.hub.domain.Hub;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepository {
    Optional<Hub> findByName(String name);
    Hub save(Hub hub);
    Optional<Hub> findById(UUID id);
    Page<Hub> findAllHub(Pageable pageable);
    Page<Hub> serachHub(String keyword, Pageable pageable);
    List<Hub> findAll();
    boolean existsByName(String name);
}
