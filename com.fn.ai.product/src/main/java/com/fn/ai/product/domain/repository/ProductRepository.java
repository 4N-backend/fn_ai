package com.fn.ai.product.domain.repository;

import com.fn.ai.product.application.dto.ProductRequestDto;
import com.fn.ai.product.domain.model.Product;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

  Product save(Product product);

  Optional<Product> findById(UUID id);

  Page<ProductSearchResponseDto> searchProduct(ProductSearchRequestDto requestDto,
      Pageable pageable);

  long increaseStock(List<ProductRequestDto> requestDto);

  long reduceStock(List<ProductRequestDto> requestDto);

  List<Product> findAll();
}
