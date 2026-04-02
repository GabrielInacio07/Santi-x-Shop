package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Product.ProductDTO;
import com.ecommerce.Santix.DTOs.Product.ProductUpdateDTO;
import com.ecommerce.Santix.DTOs.Product.ProductoResponseDTO;
import com.ecommerce.Santix.model.Product;
import com.ecommerce.Santix.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Void> salvarProduct(@RequestBody @Valid ProductDTO productDTO, @RequestHeader("userId") Long userId){
        service.saveProduct(productDTO, userId);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarProduct(@PathVariable Long id, @RequestHeader("userId") Long userId){

        Product product = service.consultProduct(id, userId);

        ProductoResponseDTO responseDTO = new ProductoResponseDTO(
                product.getUser().getId(),
                product.getTitle(),
                product.getDescription(),
                product.getSku(),
                product.getPrice()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> buscarAllProduts(@RequestHeader("userId") Long userId){
        List<Product> products = service.consultAllProduct(userId);

        List<ProductoResponseDTO> responseDTO = products.stream()
                .map(product -> new ProductoResponseDTO(
                        product.getUser().getId(),
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
            @RequestBody ProductUpdateDTO productDTO,
            @RequestHeader("userId") Long userId){

        service.updateProduct(id, productDTO, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestHeader("userId") Long userId){
        service.deleteProduct(id,userId);

        return ResponseEntity.noContent().build();
    }
}
