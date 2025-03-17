package com.fn.ai.product.presentation;

import com.fn.ai.product.application.service.ProductService;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductCreateResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateResponseDto;
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
  public ResponseEntity<ProductCreateResponseDto> createProduct(
      @RequestBody ProductCreateRequestDto product) {
    return ResponseEntity.ok().body(productService.createProduct(product));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> findProductById(
      @PathVariable UUID productId) {
    return ResponseEntity.ok().body(productService.findProductById(productId));
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductUpdateResponseDto> updateProduct(
      @RequestBody ProductUpdateRequestDto requestDto,
      @PathVariable UUID productId) {
    return ResponseEntity.ok().body(productService.updateProduct(requestDto, productId));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok().build();
  }
}
