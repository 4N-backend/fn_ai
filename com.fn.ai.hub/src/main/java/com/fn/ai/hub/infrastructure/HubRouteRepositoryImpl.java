package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.HubRoute;
import com.fn.ai.hub.domain.QHubRoute;
import com.fn.ai.hub.domain.repository.HubRouteRepository;
import com.fn.ai.hub.infrastructure.jpa.HubRouJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRouteRepositoryImpl implements HubRouteRepository {

    private final HubRouJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public HubRoute save(HubRoute route) {
        return jpaRepository.save(route);
    }

    @Override
    public boolean existsByDepatureHubIdAndArrivalHubId(UUID departureHubId, UUID arrivalHubId) {
        QHubRoute hubRoute = QHubRoute.hubRoute;
        Integer fetchOne = queryFactory
                .selectOne()
                .from(hubRoute)
                .where(hubRoute.departureHubId.eq(departureHubId)
                        .and(hubRoute.arrivalHubId.eq(arrivalHubId)))
                .fetchFirst();
        return fetchOne != null;
    }

    @Override
    public List<HubRoute> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<HubRoute> findByDepatureHubIdAndArrivalHubId(UUID departureHubId, UUID arrivalHubId) {
        QHubRoute hubRoute = QHubRoute.hubRoute;
        HubRoute result = queryFactory
                .selectFrom(hubRoute)
                .where(
                        hubRoute.departureHubId.eq(departureHubId)
                                .and(hubRoute.arrivalHubId.eq(arrivalHubId))
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<HubRoute> findAllByPage(Pageable pageable) {
        QHubRoute hubRoute = QHubRoute.hubRoute;

        List<HubRoute> hubRoutes = queryFactory
                .selectFrom(hubRoute)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(hubRoute.count())
                .from(hubRoute)
                .fetchOne();
        return new PageImpl<>(hubRoutes,pageable,total != null ? total : 0);
    }

    @Override
    public Optional<HubRoute> findHubRouteById(UUID routeId) {
        return null;
    }

    @Override
    public Page<HubRoute> searchHubRoute(Pageable pageable, UUID keyword) {
        QHubRoute hubRoute = QHubRoute.hubRoute;

        List<HubRoute> hubRoutes = queryFactory
            .selectFrom(hubRoute)
            .where(hubRoute.departureHubId.eq(keyword))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(hubRoute.count())
            .from(hubRoute)
            .where(hubRoute.departureHubId.eq(keyword))
            .fetchOne();

        return new PageImpl<>(hubRoutes, pageable, total != null ? total : 0);
    }

    @Override
    public Optional<HubRoute> findById(UUID routeId) {
        return jpaRepository.findById(routeId);
    }
}
