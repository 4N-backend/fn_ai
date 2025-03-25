package com.fn.ai.product.presentation.dto.request;

import com.fn.ai.product.domain.model.Product;
import lombok.Builder;

@Builder
public record ProductCreateResponseDto(String productName,
                                       int stock) {

  public static ProductCreateResponseDto from(Product product) {
    return ProductCreateResponseDto.builder()
        .productName(product.getName())
        .stock(product.getStock())
        .build();
  }
}
