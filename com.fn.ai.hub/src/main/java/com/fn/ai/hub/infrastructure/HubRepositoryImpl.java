package com.fn.ai.hub.infrastructure;

import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.QHub;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.infrastructure.jpa.HubJpaRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            .where(
                hub.name.value.eq(name),
                hub.deletedAt.isNull()
            )
            .fetchOne());
    }

    @Override
    public Hub save(Hub hub) {
        return jpaRepository.save(hub);
    }

    @Override
    public Optional<Hub> findById(UUID id) {
        QHub hub = QHub.hub;

        return Optional.ofNullable(queryFactory
            .selectFrom(hub)
            .where(
                hub.id.eq(id),
                hub.deletedAt.isNull()
            )
            .fetchOne());
    }

    @Override
    public Page<Hub> findAllHub(Pageable pageable) {
        QHub hub = QHub.hub;

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort(), hub);

        List<Hub> hubs = queryFactory
            .selectFrom(hub)
            .where(hub.deletedAt.isNull())
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(hub.count())
            .from(hub)
            .where(hub.deletedAt.isNull())
            .fetchOne();

        return new PageImpl<>(hubs, pageable, total != null ? total : 0);
    }

    @Override
    public Page<Hub> serachHub(String keyword, Pageable pageable) {
        QHub hub = QHub.hub;

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort(), hub);

        List<Hub> hubs = queryFactory
            .selectFrom(hub)
            .where(
                hub.name.value.containsIgnoreCase(keyword),
                hub.deletedAt.isNull()
            )
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(hub.count())
            .from(hub)
            .where(
                hub.name.value.containsIgnoreCase(keyword),
                hub.deletedAt.isNull()
            )
            .fetchOne();

        return new PageImpl<>(hubs, pageable, total != null ? total : 0);
    }

    @Override
    public List<Hub> findAll() {
        QHub hub = QHub.hub;

        return queryFactory
            .selectFrom(hub)
            .where(hub.deletedAt.isNull())
            .fetch();
    }

    @Override
    public boolean existsByName(String name) {
        QHub hub = QHub.hub;
        Integer fetchOne = queryFactory
            .selectOne()
            .from(hub)
            .where(
                hub.name.value.eq(name),
                hub.deletedAt.isNull()
            )
            .fetchFirst();

        return fetchOne != null;
    }


    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort, QHub hub) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty().toLowerCase();

            // VO 내부 필드 분기 처리 (확장 가능)
            switch (property) {
                case "id" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.id));
                case "name" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.name.value));
                case "createdat" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.createdAt));
                case "address" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.address.value));
                case "createdby" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.createdBy));
                case "latitude" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.location.latitude));
                case "longitude" -> orderSpecifiers.add(new OrderSpecifier<>(direction, hub.location.longitude));
                default -> throw new IllegalArgumentException("정렬 불가능한 필드: "+property+ "\n"
                    + "정렬 가능한 필드들 리스트 : id,name,createdAt,createdBy,address,latitude,longitude");
            }
        }

        return orderSpecifiers;
    }
}

