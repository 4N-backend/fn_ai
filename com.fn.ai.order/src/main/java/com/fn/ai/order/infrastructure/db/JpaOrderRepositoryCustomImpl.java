package com.fn.ai.order.infrastructure.db;

import static com.fn.ai.order.model.QOrder.order;
import static com.fn.ai.order.model.QOrderItem.orderItem;

import com.fn.ai.order.model.Order;
import com.fn.ai.order.model.OrderSortType;
import com.fn.ai.order.presentation.OrderSearchRequestDto;
import com.fn.ai.order.presentation.OrderSearchResponseDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Slf4j
public class JpaOrderRepositoryCustomImpl implements JpaOrderRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<OrderSearchResponseDto> searchOrder(OrderSearchRequestDto requestDto,
      Pageable pageable) {

    int pageSize = validatePageSize(pageable.getPageSize());

    Long totalCount = queryFactory
        .select(order.count())
        .from(order)
        .where(
            orderIdEq(requestDto.orderId()),
            receiverIdEq(requestDto.receiverId()),
            supplierIdEq(requestDto.supplierId()),
            deliveryIdEq(requestDto.deliveryId()))
        .fetchOne();

    List<Order> fetch = queryFactory
        .selectFrom(order)
        .leftJoin(order.orderItemList, orderItem).fetchJoin()
        .where(
            orderIdEq(requestDto.orderId()),
            receiverIdEq(requestDto.receiverId()),
            supplierIdEq(requestDto.supplierId()),
            deliveryIdEq(requestDto.deliveryId()))
        .orderBy(createOrderSpecifier(pageable).toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageSize)
        .distinct()
        .fetch();

    List<OrderSearchResponseDto> responseDtoList =
        fetch.stream().map(OrderSearchResponseDto::from)
            .collect(Collectors.toList());
    return new PageImpl<>(responseDtoList, pageable, totalCount == null ? 0 : totalCount);
  }

  private List<OrderSpecifier<?>> createOrderSpecifier(Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();

    Map<String, OrderSortType> sortTypeMap = Map.of(
        OrderSortType.CreateAt.getName(), OrderSortType.CreateAt,
        OrderSortType.DeleteAt.getName(), OrderSortType.DeleteAt
    );

    if (pageable.getSort().isSorted()) {
      pageable.getSort().forEach(order -> {
        if (sortTypeMap.containsKey(order.getProperty())) {
          orderSpecifierList.add(sortTypeMap.get(order.getProperty())
              .getOrderSpecifier(order.isAscending()));
        }
      });
    }
    return orderSpecifierList;
  }

  private int validatePageSize(int pageSize) {
    return Set.of(10, 30, 50).contains(pageSize) ? pageSize : 10;
  }

  private BooleanExpression orderIdEq(UUID orderId) {
    return Objects.nonNull(orderId) ? order.id.eq(orderId) : null;
  }

  private BooleanExpression receiverIdEq(UUID receiverId) {
    return Objects.nonNull(receiverId) ? order.receiverId.eq(receiverId) : null;
  }

  private BooleanExpression supplierIdEq(UUID supplierId) {
    return Objects.nonNull(supplierId) ? order.supplierId.eq(supplierId) : null;
  }

  private BooleanExpression deliveryIdEq(UUID deliveryId) {
    return Objects.nonNull(deliveryId) ? order.deliveryId.eq(deliveryId) : null;
  }

}
