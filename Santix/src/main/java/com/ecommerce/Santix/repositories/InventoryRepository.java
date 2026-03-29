package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findByProductUserIdAndStockUserId(Long userId, Long userId2);
}
