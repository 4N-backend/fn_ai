package com.fn.ai.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.fn.ai.product.application.dto.ProductRequestDto;
import com.fn.ai.product.common.UnitTestSupport;
import com.fn.ai.product.model.Product;
import com.fn.ai.product.model.repository.ProductRepository;
import com.fn.ai.product.presentation.dto.request.ProductCreateRequestDto;
import com.fn.ai.product.presentation.dto.request.ProductSearchRequestDto;
import com.fn.ai.product.presentation.dto.response.ProductSearchResponseDto;
import com.fn.ai.product.presentation.dto.response.ProductUpdateRequestDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

class ProductApplicationTests extends UnitTestSupport {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;


  @Test
  @DisplayName("상품생성")
  @Transactional
  void createProduct() {
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    ProductCreateRequestDto requestDto = ProductCreateRequestDto.builder()
        .productName("꼬북칩")
        .stock(100)
        .hubId(hubId)
        .companyId(companyId)
        .build();

    productRepository.save(Product.from(requestDto));

    List<Product> all = productRepository.findAll();
    assertThat(all).hasSize(1)
        .extracting("name", "stock", "hubId", "companyId")
        .containsExactlyInAnyOrder(
            tuple("꼬북칩", 100, hubId, companyId)
        );
  }

  @Test
  @DisplayName("상품수정")
  @Transactional
  void updateProduct() {
    //given
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    Product product = productRepository.save(Product.from(ProductCreateRequestDto.builder()
        .productName("꼬북칩")
        .stock(100)
        .hubId(hubId)
        .companyId(companyId)
        .build()));

    //when
    UUID updateHubId = UUID.randomUUID();
    UUID updateCompanyId = UUID.randomUUID();
    product.update(new ProductUpdateRequestDto("감자칩", 20, updateHubId, updateCompanyId));

    //then
    List<Product> all = productRepository.findAll();
    assertThat(all).hasSize(1)
        .extracting("name", "stock", "hubId", "companyId")
        .containsExactlyInAnyOrder(
            tuple("감자칩", 20, updateHubId, updateCompanyId)
        );
  }

  @Test
  @DisplayName("상품검색")
  @Transactional
  void search() {
    //given
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    for (int i = 1; i <= 10; i++) {
      productRepository.save(Product.from(ProductCreateRequestDto.builder()
          .productName("꼬북칩" + i)
          .stock(100 + i * 5)
          .hubId(hubId)
          .companyId(companyId)
          .build()));
    }

    ProductSearchRequestDto requestDto = new ProductSearchRequestDto("꼬북", 100);

    Page<ProductSearchResponseDto> productPage = productService.search(requestDto,
        PageRequest.of(0, 3, Sort.by("stock").descending()));

    assertThat(productPage).isNotEmpty();
    assertThat(productPage.getTotalElements()).isEqualTo(10);
    assertThat(productPage.getTotalPages()).isEqualTo(4);
    assertThat(productPage.getContent().get(0).name()).isEqualTo("꼬북칩10");
    assertThat(productPage.getContent().get(0).stock()).isEqualTo(150);
    assertThat(productPage.getContent().get(2).name()).isEqualTo("꼬북칩8");
  }

  @Test
  @DisplayName("재고 감소 동시성 이슈 테스트")
  void reduceProduct() throws InterruptedException {
    //given
    UUID hubId = UUID.randomUUID();
    UUID companyId = UUID.randomUUID();

    Product product = productRepository.save(Product.from(ProductCreateRequestDto.builder()
        .productName("꼬북칩")
        .stock(1000)
        .hubId(hubId)
        .companyId(companyId)
        .build()));

    int stock = productRepository.findById(product.getId()).get().getStock();

    int repeat = 100;
    int decreaseAmount = 2;
    ExecutorService executorService = Executors.newFixedThreadPool(9);
    CountDownLatch countDownLatch = new CountDownLatch(repeat);

    for (int i = 0; i < repeat; i++) {
      executorService.execute(() -> {
        try {
          productService.reduceStock(getList(product.getId(), decreaseAmount));
        } finally {
          countDownLatch.countDown();
        }
      });
    }

    countDownLatch.await();

    Product product1 = productRepository.findById(product.getId()).orElseThrow();
    assertThat(stock - repeat * decreaseAmount).isEqualTo(product1.getStock());

  }

  public List<ProductRequestDto> getList(UUID productId, int stock) {
    List<ProductRequestDto> list = new ArrayList<>();
    list.add(new ProductRequestDto(productId, stock));
    return list;
  }
  
}