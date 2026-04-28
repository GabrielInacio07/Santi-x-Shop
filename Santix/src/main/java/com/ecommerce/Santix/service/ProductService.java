package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Product.ProductDTO;
import com.ecommerce.Santix.DTOs.Product.ProductUpdateDTO;
import com.ecommerce.Santix.Exception.EntityNotFound;
import com.ecommerce.Santix.Exception.UnauthorizedException;
import com.ecommerce.Santix.model.Product;
import com.ecommerce.Santix.model.Role;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.ProductRepository;
import com.ecommerce.Santix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("Usuário não autenticado");
        }

        return (User) authentication.getPrincipal();
    }

    private void validateProduct(ProductDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Título do Produto não pode estar vazio");
        }

        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Descrição do Produto não pode estar vazio");
        }

        if (dto.getSku() == null || dto.getSku().isBlank()) {
            throw new IllegalArgumentException("SKU do Produto não pode estar vazio");
        }

        if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do Produto deve ser maior que zero");
        }

    }

    private User isSellerOrThrow() {

        User user = getAuthenticatedUser();

        if (user.getRole() != Role.SELLER && user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Apenas SELLER ou ADMIN podem realizar essa operação");
        }


        return user;
    }

    private void productOwner(Product product, User user) {

        if (user.getRole() == Role.ADMIN) return;

        if (!product.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este Produto");
        }
    }

    private void validateSku(String sku) {
        if (productRepository.findBySku(sku).isPresent()) {
            throw new IllegalArgumentException("SKU já existe");
        }
    }

    public void saveProduct(ProductDTO productDTO) {

        User user = isSellerOrThrow();
        validateProduct(productDTO);
        validateSku(productDTO.getSku().trim());

         Product product = Product.builder()
                .user(user)
                .title(productDTO.getTitle().trim())
                .description(productDTO.getDescription().trim())
                .sku(productDTO.getSku().trim())
                .price(productDTO.getPrice())
                .build();

        productRepository.save(product);
    }

    public Product consultProduct(long id) {
        User user = isSellerOrThrow();
        Product productEntity = productRepository.findById(id).orElseThrow(() -> new EntityNotFound("Produto não encontrado"));

        productOwner(productEntity, user);
        return productEntity;
    }

    public List<Product> consultAllProduct() {
        User user = isSellerOrThrow();
        return productRepository.findByUserId(user.getId());
    }

    public void updateProduct(Long id, ProductUpdateDTO productDTO) {
        User user = isSellerOrThrow();

        Product productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFound("Produto não encontrado"));

        productOwner(productEntity, user);

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

    public void deleteProduct(Long id) {
        User user = isSellerOrThrow();

        Product productEntity = productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFound("Produto não encontrado"));

        productOwner(productEntity, user);

        productRepository.deleteById(id);

    }
}
