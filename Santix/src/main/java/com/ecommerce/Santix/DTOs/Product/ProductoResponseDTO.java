package com.ecommerce.Santix.DTOs.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductoResponseDTO {
    private String title;
    private String description;
    private String sku;
    private BigDecimal price;
}
