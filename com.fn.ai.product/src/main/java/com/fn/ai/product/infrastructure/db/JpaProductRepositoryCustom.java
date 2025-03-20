package com.fn.ai.product.infrastructure.db;

import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaProductRepositoryCustom {

  Page<ProductSearchResponseDto> searchProduct(ProductSearchRequestDto requestDto,
      Pageable pageable);
}
