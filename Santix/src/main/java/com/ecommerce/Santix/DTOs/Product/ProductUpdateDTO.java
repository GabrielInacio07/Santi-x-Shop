package com.ecommerce.Santix.DTOs.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProductUpdateDTO {
    private String title;
    private String description;
    private String sku;
    private BigDecimal price;
}
