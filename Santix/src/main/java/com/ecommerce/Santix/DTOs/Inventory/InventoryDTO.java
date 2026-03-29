package com.ecommerce.Santix.DTOs.Inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryDTO {

    private int quantity;
    private Long productId;
    private Long stockId;
}
