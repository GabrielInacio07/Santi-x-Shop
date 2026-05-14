package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByUserId(Long userId);
    Optional<Product> findBySku(String sku);

    @Query("select p from Product p where p.user.id = :userId and p.price > :price")
    List<Product> getProductsMaxPrice(Long userId, BigDecimal price);
}

