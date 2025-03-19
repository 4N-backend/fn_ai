package com.fn.ai.product.model;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity(name = "p_product")
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "product_name", nullable = false)
  private String name;

  @Column(nullable = false)
  private UUID hubId;

  @Column(nullable = false)
  private UUID companyId;

  private int stock;


  public static Product from(ProductCreateRequestDto requestDto) {
    return Product.builder()
        .name(requestDto.productName())
        .stock(requestDto.stock())
        .companyId(requestDto.companyId())
        .hubId(requestDto.hubId())
        .build();
  }

  public Product update(ProductUpdateRequestDto requestDto) {
    this.name = requestDto.productName();
    this.stock = requestDto.stock();
    this.companyId = requestDto.companyId();
    this.hubId = requestDto.hubId();
    return this;
  }
}
