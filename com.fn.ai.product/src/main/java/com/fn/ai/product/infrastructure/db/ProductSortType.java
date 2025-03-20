package com.fn.ai.product.infrastructure.db;

import static com.fn.ai.product.model.QProduct.product;

import com.querydsl.core.types.OrderSpecifier;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSortType {

  PRODUCT_NAME("productName",
      direction -> direction ? product.name.asc() : product.name.desc()),
  PRODUCT_STOCK("stock",
      direction -> direction ? product.stock.asc() : product.stock.desc());

  @Getter
  private final String name;
  private final Function<Boolean, OrderSpecifier<?>> expression;

  public OrderSpecifier<?> getOrderSpecifier(boolean ascending) {
    return expression.apply(ascending);
  }
}