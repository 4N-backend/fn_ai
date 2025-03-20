package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.QHub;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.infrastructure.jpa.HubJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * TODO
 * BaseEntity적용하고 soft delete적용하기
 */
@RequiredArgsConstructor
@Repository
public class HubRepositoryImpl implements HubRepository {

    private final HubJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    /**
     * TODO softdelete적용 -> delete_by가 null인 값만 찾도록 (모든 메서드)
     */

    @Override
    public Optional<Hub> findByName(String name) {
        QHub hub = QHub.hub;
        return Optional.ofNullable(queryFactory
            .selectFrom(hub)
            .where(hub.name.value.eq(name))
            .fetchOne());
    }

    @Override
    public Hub save(Hub hub) {
        return jpaRepository.save(hub);
    }

    @Override
    public Optional<Hub> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Page<Hub> findAllHub(Pageable pageable) {
        QHub hub = QHub.hub;

        List<Hub> hubs = queryFactory
            .selectFrom(hub)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(hub.count())
            .from(hub)
            .fetchOne();

        return new PageImpl<>(hubs, pageable, total != null ? total : 0);
    }

    @Override
    public Page<Hub> serachHub(String keyword, Pageable pageable) {
        QHub hub = QHub.hub;

        List<Hub> hubs =queryFactory
            .selectFrom(hub)
            .where(hub.name.value.containsIgnoreCase(keyword))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total =queryFactory
            .select(hub.count())
            .from(hub)
            .where(hub.name.value.containsIgnoreCase(keyword))
            .fetchOne();

        return new PageImpl<>(hubs, pageable, total != null ? total : 0);
    }

    @Override
    public List<Hub> findAll() {

        return jpaRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        QHub hub = QHub.hub;
        Integer fetchOne = queryFactory
                .selectOne()
                .from(hub)
                .where(hub.name.value.eq(name))
                .fetchFirst();
        return fetchOne != null;
    }
}

