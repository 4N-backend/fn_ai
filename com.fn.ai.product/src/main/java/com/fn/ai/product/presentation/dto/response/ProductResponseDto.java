package com.fn.ai.product.presentation.dto.response;

import com.fn.ai.product.model.Product;
import lombok.Builder;

@Builder
public record ProductResponseDto(String productName,
                                 int stock) {

  public static ProductResponseDto from(Product product) {
    return ProductResponseDto.builder()
        .productName(product.getName())
        .stock(product.getStock())
        .build();
  }
}
