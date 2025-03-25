package com.fn.ai.product.application.service;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.exception.BaseException;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.product.application.client.CompanyClient;
import com.fn.ai.product.application.client.HubClient;
import com.fn.ai.product.application.dto.ProductRequestDto;
import com.fn.ai.product.domain.model.Product;
import com.fn.ai.product.domain.repository.ProductRepository;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductCreateResponseDto;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {

  private final HubClient hubClient;

  private final CompanyClient companyClient;

  private final ProductRepository productRepository;

  public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestDto) {

    hubClient.getHubById(requestDto.hubId()).orElseThrow(() ->
        new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), "HubId 요청에 실패했습니다."));

    companyClient.getCompanyById(requestDto.companyId()).orElseThrow(() ->
        new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), "CompanyId 요청에 실패했습니다."));

    return ProductCreateResponseDto.from(productRepository.save(Product.from(requestDto)));
  }

  @Transactional(readOnly = true)
  public ProductResponseDto findProductById(UUID productId) {
    Product product = productRepository.findById(productId).orElseThrow(() ->
        new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(), "상품을 찾을 수 없습니다."));

    return ProductResponseDto.from(product);
  }

  public ProductUpdateResponseDto updateProduct(ProductUpdateRequestDto requestDto,
      UUID productId) {

    Product product = productRepository.findById(productId).orElseThrow(() ->
        new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(), "상품을 찾을 수 없습니다."));

    product.update(requestDto);
    return ProductUpdateResponseDto.from(product);
  }

  public ProductResponseDto deleteProduct(UUID productId, UserContext userInfo) {
    Product product = productRepository.findById(productId).orElseThrow(() ->
        new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(), "상품을 찾을 수 없습니다."));

    product.delete();

    return ProductResponseDto.from(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductSearchResponseDto> search(ProductSearchRequestDto requestDto,
      Pageable pageable) {
    return productRepository.searchProduct(requestDto, pageable);
  }

  public Boolean reduceStock(List<ProductRequestDto> requestDto) {
    for (ProductRequestDto productDto : requestDto) {

      int stock = productRepository.findById(productDto.productId()).orElseThrow(() ->
              new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(), "상품을 찾을 수 없습니다."))
          .getStock();

      if (stock < productDto.stock()) {
        throw new BaseException(CommonResponseCode.SERVER_ERROR.getCode(), "재고가 부족합니다.");
      }
    }
    return productRepository.reduceStock(requestDto) == requestDto.size();
  }

  public Boolean increaseStock(List<ProductRequestDto> requestDto) {
    return productRepository.increaseStock(requestDto) == requestDto.size();
  }
}
