package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByUserId(Long userId);

}
