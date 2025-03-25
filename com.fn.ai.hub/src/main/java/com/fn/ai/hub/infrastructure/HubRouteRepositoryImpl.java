package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.HubRoute;
import com.fn.ai.hub.domain.QHubRoute;
import com.fn.ai.hub.domain.repository.HubRouteRepository;
import com.fn.ai.hub.infrastructure.jpa.HubRouJpaRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
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

        Integer result = queryFactory
            .selectOne()
            .from(hubRoute)
            .where(
                hubRoute.departureHubId.eq(departureHubId),
                hubRoute.arrivalHubId.eq(arrivalHubId),
                hubRoute.deletedAt.isNull()
            )
            .fetchFirst();

        return result != null;
    }

    @Override
    public List<HubRoute> findAll() {
        QHubRoute hubRoute = QHubRoute.hubRoute;

        return queryFactory
            .selectFrom(hubRoute)
            .where(hubRoute.deletedAt.isNull())
            .fetch();
    }

    @Override
    public Optional<HubRoute> findByDepatureHubIdAndArrivalHubId(UUID departureHubId, UUID arrivalHubId) {
        QHubRoute hubRoute = QHubRoute.hubRoute;

        HubRoute result = queryFactory
            .selectFrom(hubRoute)
            .where(
                hubRoute.departureHubId.eq(departureHubId),
                hubRoute.arrivalHubId.eq(arrivalHubId),
                hubRoute.deletedAt.isNull()
            )
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<HubRoute> findHubRouteById(UUID routeId) {
        QHubRoute hubRoute = QHubRoute.hubRoute;

        HubRoute result = queryFactory
            .selectFrom(hubRoute)
            .where(
                hubRoute.id.eq(routeId),
                hubRoute.deletedAt.isNull()
            )
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<HubRoute> findById(UUID routeId) {
        QHubRoute hubRoute = QHubRoute.hubRoute;

        HubRoute result = queryFactory
            .selectFrom(hubRoute)
            .where(
                hubRoute.id.eq(routeId),
                hubRoute.deletedAt.isNull()
            )
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<HubRoute> findAllByPage(Pageable pageable) {
        QHubRoute hubRoute = QHubRoute.hubRoute;
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort(), hubRoute);

        List<HubRoute> content = queryFactory
            .selectFrom(hubRoute)
            .where(hubRoute.deletedAt.isNull())
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(hubRoute.count())
            .from(hubRoute)
            .where(hubRoute.deletedAt.isNull())
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Page<HubRoute> searchHubRoute(Pageable pageable, String keyword) {
        QHubRoute hubRoute = QHubRoute.hubRoute;
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort(), hubRoute);

        List<HubRoute> content = queryFactory
            .selectFrom(hubRoute)
            .where(
                hubRoute.departureHubName.eq(keyword),
                hubRoute.deletedAt.isNull()
            )
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(hubRoute.count())
            .from(hubRoute)
            .where(
                hubRoute.departureHubName.eq(keyword),
                hubRoute.deletedAt.isNull()
            )
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort, QHubRoute hubRoute) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty().toLowerCase();

            switch (property) {
                case "id" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.id));
                case "departurehubid" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.departureHubId));
                case "arrivalhubid" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.arrivalHubId));
                case "departurehubname" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.departureHubName));
                case "arrivalhubname" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.arrivalHubName));
                case "distance" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.distance.value));
                case "traveltime" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.travelTime.value));
                case "createdat" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.createdAt));
                case "createdby" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hubRoute.createdBy));
                default -> throw new IllegalArgumentException("정렬 불가능한 필드: " + property + "\n"
                    + "정렬 가능한 필드: id, departureHubId,departureHubName, arrivalHubId,arrivalHubName, distance, travelTime, createdAt, createdBy");
            }
        }

        return orderSpecifiers;
    }
}
