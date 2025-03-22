package com.fn.ai.product.presentation.dto.response;

import com.fn.ai.product.model.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductSearchResponseDto(
    UUID productId,
    String name,
    int stock,
    String createdBy,
    LocalDateTime createdAt,
    String updatedBy,
    LocalDateTime updatedAt
) {

  public static ProductSearchResponseDto from(Product product) {
    return ProductSearchResponseDto.builder()
        .productId(product.getId())
        .name(product.getName())
        .stock(product.getStock())
        .createdBy(product.getCreatedBy())
        .createdAt(product.getCreatedAt())
        .updatedBy(product.getUpdatedBy())
        .updatedAt(product.getUpdatedAt())
        .build();
  }

}
