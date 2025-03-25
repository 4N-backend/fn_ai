package com.fn.ai.product.domain;

import static com.fn.ai.product.domain.model.QProduct.product;

import com.querydsl.core.types.OrderSpecifier;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSortType {

  PRODUCT_NAME("productName",
      direction -> direction ? product.name.asc() : product.name.desc()),
  UPDATED_AT("updatedAt",
      direction -> direction ? product.updatedAt.asc() : product.updatedAt.desc()),
  CREATED_AT("createdAt",
      direction -> direction ? product.createdAt.asc() : product.createdAt.desc()),
  PRODUCT_STOCK("stock",
      direction -> direction ? product.stock.asc() : product.stock.desc());

  @Getter
  private final String name;
  private final Function<Boolean, OrderSpecifier<?>> expression;

  public OrderSpecifier<?> getOrderSpecifier(boolean ascending) {
    return expression.apply(ascending);
  }
}