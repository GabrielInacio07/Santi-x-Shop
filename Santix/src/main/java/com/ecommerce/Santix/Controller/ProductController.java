package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Product.ProductDTO;
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

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Void> salvarProduct(@RequestBody @Valid ProductDTO productDTO, @AuthenticationPrincipal User user){
        service.saveProduct(productDTO, user.getId());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> buscarProduct(@PathVariable Long id, @AuthenticationPrincipal User user){

        Product product = service.consultProduct(id, user.getId());

        ProductoResponseDTO responseDTO = new ProductoResponseDTO(
                product.getTitle(),
                product.getDescription(),
                product.getSku(),
                product.getPrice()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> buscarAllProduts(@AuthenticationPrincipal User user){
        List<Product> products = service.consultAllProduct(user.getId());

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
            @RequestBody ProductUpdateDTO productDTO,
            @AuthenticationPrincipal User user){

        service.updateProduct(id, productDTO, user.getId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal User user){
        service.deleteProduct(id,user.getId());

        return ResponseEntity.noContent().build();
    }
}
