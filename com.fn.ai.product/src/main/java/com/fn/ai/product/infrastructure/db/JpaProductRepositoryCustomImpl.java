package com.fn.ai.product.infrastructure.db;

import static com.fn.ai.product.domain.model.QProduct.product;

import com.fn.ai.common.exception.BaseException;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.product.application.dto.ProductRequestDto;
import com.fn.ai.product.domain.ProductSortType;
import com.fn.ai.product.domain.model.Product;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Slf4j
public class JpaProductRepositoryCustomImpl implements JpaProductRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<ProductSearchResponseDto> searchProduct(ProductSearchRequestDto requestDto,
      Pageable pageable) {

    int pageSize = validatePageSize(pageable.getPageSize());

    Long totalCount = queryFactory
        .select(product.count())
        .from(product)
        .where(
            containsProductName(requestDto.productName()),
            product.stock.goe(requestDto.stock())
        )
        .fetchOne();

    List<Product> fetch = queryFactory
        .selectFrom(product)
        .where(
            containsProductName(requestDto.productName()),
            product.stock.goe(requestDto.stock()))
        .orderBy(createOrderSpecifier(pageable).toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageSize)
        .distinct()
        .fetch();

    List<ProductSearchResponseDto> responseDtoList =
        fetch.stream().map(ProductSearchResponseDto::from)
            .collect(Collectors.toList());

    return new PageImpl<>(responseDtoList, pageable, totalCount == null ? 0 : totalCount);
  }

  @Override
  public long reduceStock(List<ProductRequestDto> requestDto) {
    return updateStock(requestDto, false);
  }

  @Override
  public long increaseStock(List<ProductRequestDto> requestDto) {
    return updateStock(requestDto, true);
  }

  public long updateStock(List<ProductRequestDto> requestDto, boolean isIncrease) {
    long updatedStock = 0;

    for (ProductRequestDto productDto : requestDto) {
      long stockChange = isIncrease ? productDto.stock() : productDto.stock() * -1;
      try {

        long execute = queryFactory
            .update(product)
            .set(product.stock, product.stock.add(stockChange))
            .where(productIdEq(productDto.productId()))
            .execute();

        updatedStock += execute;

      } catch (DataIntegrityViolationException e) {
        throw new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), e.getMessage());
      }
    }

    return updatedStock;
  }

  private BooleanExpression productIdEq(UUID productId) {
    return Objects.nonNull(productId) ? product.id.eq(productId) : null;
  }

  private BooleanExpression containsProductName(String productName) {
    return Objects.nonNull(productName) ? product.name.containsIgnoreCase(productName) : null;
  }

  private int validatePageSize(int pageSize) {
    return Set.of(10, 30, 50).contains(pageSize) ? pageSize : 10;
  }

  private List<OrderSpecifier<?>> createOrderSpecifier(Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();

    Map<String, ProductSortType> sortTypeMap = Map.of(
        ProductSortType.PRODUCT_NAME.getName(), ProductSortType.PRODUCT_NAME,
        ProductSortType.PRODUCT_STOCK.getName(), ProductSortType.PRODUCT_STOCK,
        ProductSortType.CREATED_AT.getName(), ProductSortType.CREATED_AT,
        ProductSortType.UPDATED_AT.getName(), ProductSortType.UPDATED_AT
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
}
