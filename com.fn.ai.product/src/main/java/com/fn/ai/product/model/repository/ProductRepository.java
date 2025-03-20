package com.fn.ai.product.model.repository;

import com.fn.ai.product.model.Product;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

  Product save(Product product);

  Optional<Product> findById(UUID id);

  Page<ProductSearchResponseDto> searchProduct(ProductSearchRequestDto requestDto,
      Pageable pageable);
}
