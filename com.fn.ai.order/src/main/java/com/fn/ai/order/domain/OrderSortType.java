package com.fn.ai.order.domain;

import static com.fn.ai.order.domain.model.QOrder.order;

import com.querydsl.core.types.OrderSpecifier;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderSortType {

  CREATE_AT("createAt",
      direction -> direction ? order.createdAt.asc() : order.createdAt.desc()),
  UPDATE_AT("deleteAt",
      direction -> direction ? order.deletedAt.asc() : order.deletedBy.desc());

  @Getter
  private final String name;
  private final Function<Boolean, OrderSpecifier<?>> expression;

  public OrderSpecifier<?> getOrderSpecifier(boolean ascending) {
    return expression.apply(ascending);
  }
}