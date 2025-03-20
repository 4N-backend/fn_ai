package com.fn.ai.product.infrastructure.db;

import com.fn.ai.product.model.Product;
import com.fn.ai.product.model.repository.ProductRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID>,
    JpaProductRepositoryCustom {

}
