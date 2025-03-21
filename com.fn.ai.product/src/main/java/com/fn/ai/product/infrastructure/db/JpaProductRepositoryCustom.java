package com.fn.ai.product.infrastructure.db;

import com.fn.ai.product.application.dto.ProductRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaProductRepositoryCustom {

  Page<ProductSearchResponseDto> searchProduct(ProductSearchRequestDto requestDto,
      Pageable pageable);

  long increaseStock(List<ProductRequestDto> requestDto);

  long reduceStock(List<ProductRequestDto> requestDto);
}
