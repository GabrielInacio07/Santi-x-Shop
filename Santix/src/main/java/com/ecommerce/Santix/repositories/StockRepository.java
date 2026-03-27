package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {

}
