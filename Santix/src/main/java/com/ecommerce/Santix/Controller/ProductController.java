package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Product.ProductDTO;
import com.ecommerce.Santix.DTOs.Product.ProductResponsePriceDTO;
import com.ecommerce.Santix.DTOs.Product.ProductUpdateDTO;
import com.ecommerce.Santix.DTOs.Product.ProductoResponseDTO;
import com.ecommerce.Santix.model.Product;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Void> salvarProduct(@RequestBody ProductDTO productDTO){
        service.saveProduct(productDTO);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarProduct(@PathVariable Long id){

        Product product = service.consultProduct(id);

        ProductoResponseDTO responseDTO = new ProductoResponseDTO(
                product.getTitle(),
                product.getDescription(),
                product.getSku(),
                product.getPrice()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/maxprice/{price}")
    public ResponseEntity<List<ProductoResponseDTO>> buscarMaiorPreco(@PathVariable BigDecimal price){

        List<Product> products = service.getProductsMaxPrice(price);

        List<ProductoResponseDTO> responseDTO = products.stream()
                .map(product -> new ProductoResponseDTO(
                        product.getTitle(),
                        product.getDescription(),
                        product.getSku(),
                        product.getPrice()
                )).toList();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> buscarAllProduts(){
        List<Product> products = service.consultAllProduct();

        List<ProductoResponseDTO> responseDTO = products.stream()
                .map(product -> new ProductoResponseDTO(
                        product.getTitle(),
                        product.getDescription(),
                        product.getSku(),
                        product.getPrice()
                        )).toList();

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateDTO productDTO){

        service.updateProduct(id, productDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
