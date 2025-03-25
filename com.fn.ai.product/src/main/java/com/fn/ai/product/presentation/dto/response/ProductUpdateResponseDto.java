package com.fn.ai.product.presentation.dto.response;

import com.fn.ai.product.domain.model.Product;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductUpdateResponseDto(String productName,
                                       UUID hubId,
                                       UUID companyId,
                                       int stock) {

  public static ProductUpdateResponseDto from(Product product) {
    return ProductUpdateResponseDto.builder()
        .productName(product.getName())
        .hubId(product.getHubId())
        .companyId(product.getCompanyId())
        .stock(product.getStock())
        .build();
  }
}
