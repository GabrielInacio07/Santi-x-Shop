package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Product.ProductDTO;
import com.ecommerce.Santix.DTOs.Product.ProductUpdateDTO;
import com.ecommerce.Santix.Exception.ProductNotFoundException;
import com.ecommerce.Santix.Exception.UserNotFoundException;
import com.ecommerce.Santix.model.Product;
import com.ecommerce.Santix.model.Role;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.ProductRepository;
import com.ecommerce.Santix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private void validateProduct(ProductDTO dto){
        if(dto.getTitle() == null || dto.getTitle().isBlank()){
            throw new IllegalArgumentException("Título do Produto não pode estar vazio");
        }

        if(dto.getDescription() == null || dto.getDescription().isBlank()){
            throw new IllegalArgumentException("Descrição do Produto não pode estar vazio");
        }

        if(dto.getSku() == null || dto.getSku().isBlank()){
            throw new IllegalArgumentException("SKU do Produto não pode estar vazio");
        }

        if(dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Preço do Produto deve ser maior que zero");
        }
    }

    private User isSellerOrThrow(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        if (user.getRole() != Role.SELLER) {
            throw new IllegalArgumentException("Apenas SELLER pode realizar essa operação");
        }

        return user;
    }

    private void productOwner(Product product, Long userId) {
        if (!product.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Você não tem permissão para acessar este Produto");
        }
    }

    public void saveProduct(ProductDTO productDTO, Long userId){

        User user = isSellerOrThrow(userId);
        validateProduct(productDTO);

        Product product = Product.builder()
                .user(user)
                .title(productDTO.getTitle().trim())
                .description(productDTO.getDescription().trim())
                .sku(productDTO.getSku().trim())
                .price(productDTO.getPrice())
                .build();

        productRepository.save(product);
    }

    public Product consultProduct(long id, Long userId){
        isSellerOrThrow(userId);
        Product productEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        productOwner(productEntity,userId);
        return productEntity;
    }

    public List<Product> consultAllProduct(Long userId){
        isSellerOrThrow(userId);
        return productRepository.findByUserId(userId);
    }

    public void updateProduct(Long id, ProductUpdateDTO productDTO, Long userId){

        Product productEntity = consultProduct(id, userId);

        productOwner(productEntity, userId);

        if (productDTO.getTitle() != null && !productDTO.getTitle().isBlank()) {
            productEntity.setTitle(productDTO.getTitle());
        }

        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()) {
            productEntity.setDescription(productDTO.getDescription());
        }

        if (productDTO.getSku() != null && !productDTO.getSku().isBlank()) {
            productEntity.setSku(productDTO.getSku());
        }

        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            productEntity.setPrice(productDTO.getPrice());
        }

        productRepository.save(productEntity);
    }

    public void deleteProduct(Long id, Long userId){
        Product productEntity = consultProduct(id, userId);

        productOwner(productEntity,userId);

        productRepository.deleteById(id);

    }
}
