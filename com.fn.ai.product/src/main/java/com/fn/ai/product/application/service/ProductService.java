package com.fn.ai.product.application.service;

import com.fn.ai.product.application.client.CompanyClient;
import com.fn.ai.product.application.client.HubClient;
import com.fn.ai.product.model.Product;
import com.fn.ai.product.model.repository.ProductRepository;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductCreateResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

  private final HubClient hubClient;

  private final CompanyClient companyClient;

  private final ProductRepository productRepository;

  public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestDto) {
    return ProductCreateResponseDto.from(productRepository.save(Product.from(requestDto)));
  }

  public ProductResponseDto findProductById(UUID productId) {
    Product product = productRepository.findById(productId).orElseThrow(() ->
        new RuntimeException("Product not found"));

    return ProductResponseDto.from(product);
  }

  public ProductUpdateResponseDto updateProduct(ProductUpdateRequestDto requestDto,
      UUID productId) {

    Product product = productRepository.findById(productId).orElseThrow(() ->
        new RuntimeException("Product not found"));

    product.update(requestDto);
    return ProductUpdateResponseDto.from(product);
  }

  public void deleteProduct(UUID productId) {
    Product product = productRepository.findById(productId).orElseThrow(() ->
        new RuntimeException("Product not found"));
    //추가예정
  }
}
