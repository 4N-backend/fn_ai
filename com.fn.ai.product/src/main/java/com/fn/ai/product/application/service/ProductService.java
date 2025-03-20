package com.fn.ai.product.application.service;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.product.application.client.CompanyClient;
import com.fn.ai.product.application.client.HubClient;
import com.fn.ai.product.model.Product;
import com.fn.ai.product.model.repository.ProductRepository;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductCreateResponseDto;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    hubClient.getHubById(requestDto.hubId()).orElseThrow(() ->
        new RuntimeException("Hub not found"));

    companyClient.getCompanyById(requestDto.companyId()).orElseThrow(() ->
        new RuntimeException("Company not found"));

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

  public ProductResponseDto deleteProduct(UUID productId, UserContext userInfo) {
    Product product = productRepository.findById(productId).orElseThrow(() ->
        new RuntimeException("Product not found"));

    product.delete();

    return ProductResponseDto.from(product);
  }

  public Page<ProductSearchResponseDto> search(ProductSearchRequestDto requestDto,
      Pageable pageable) {
    return productRepository.searchProduct(requestDto, pageable);
  }
}
