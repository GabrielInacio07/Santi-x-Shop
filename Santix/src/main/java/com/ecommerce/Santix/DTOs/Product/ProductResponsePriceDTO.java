package com.ecommerce.Santix.DTOs.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductResponsePriceDTO {

    private String title;
    private BigDecimal price;
}
