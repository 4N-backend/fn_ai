package com.fn.ai.product.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.annotation.CurrentUserInfo;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.product.application.service.ProductService;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductCreateResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateResponseDto;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductContoller {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<CommonResponse<ProductCreateResponseDto>> createProduct(
      @Valid @RequestBody ProductCreateRequestDto product) {
    return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
        CommonResponseCode.CREATED.getMessage(), productService.createProduct(product));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<CommonResponse<ProductResponseDto>> findProductById(
      @PathVariable UUID productId) {
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
        CommonResponseCode.SUCCESS.getMessage(), productService.findProductById(productId));
  }

  @PutMapping("/{productId}")
  public ResponseEntity<CommonResponse<ProductUpdateResponseDto>> updateProduct(
      @RequestBody ProductUpdateRequestDto requestDto,
      @PathVariable UUID productId) {
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
        CommonResponseCode.SUCCESS.getMessage(),
        productService.updateProduct(requestDto, productId));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<CommonResponse<ProductResponseDto>> deleteProduct(
      @PathVariable UUID productId,
      @CurrentUserInfo UserContext userInfo) {

    return CommonResponse.of(CommonResponseCode.NO_CONTENT.getCode(),
        CommonResponseCode.NO_CONTENT.getMessage(),
        productService.deleteProduct(productId, userInfo));
  }
}
