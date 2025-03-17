package com.fn.ai.product.model.repository;

import com.fn.ai.product.model.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

  Product save(Product product);

  Optional<Product> findById(UUID id);

}
