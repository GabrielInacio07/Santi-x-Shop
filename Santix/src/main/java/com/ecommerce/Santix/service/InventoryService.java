package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Inventory.InventoryDTO;
import com.ecommerce.Santix.DTOs.Inventory.InventoryUpdateDTO;
import com.ecommerce.Santix.Exception.EntityNotFound;
import com.ecommerce.Santix.Exception.UnauthorizedException;
import com.ecommerce.Santix.model.*;
import com.ecommerce.Santix.repositories.InventoryRepository;
import com.ecommerce.Santix.repositories.ProductRepository;
import com.ecommerce.Santix.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("Usuário não autenticado");
        }

        return (User) authentication.getPrincipal();
    }

    private User isSellerOrThrow() {
        User user = getAuthenticatedUser();

        if (user.getRole() != Role.SELLER && user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Apenas SELLER ou ADMIN podem realizar essa operação");
        }

        return user;
    }

    private void ownerStockProduct(Product product, Stock stock) {
        User user = isSellerOrThrow();

        if (user.getRole() == Role.ADMIN) return;

        if (!product.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Produto não pertence ao usuário");
        }

        if (!stock.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Stock não pertence ao usuário");
        }
    }

    private void isValidInventory(Inventory inventory) {
        User user = isSellerOrThrow();

        if (user.getRole() == Role.ADMIN) return;

        if (!inventory.getProduct().getUser().getId().equals(user.getId()) ||
                !inventory.getStock().getUser().getId().equals(user.getId())) {

            throw new UnauthorizedException("Você não tem permissão para acessar este inventário");
        }
    }

    public void saveInventory(InventoryDTO inventoryDTO) {

        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new EntityNotFound("Produto não encontrado"));

        Stock stock = stockRepository.findById(inventoryDTO.getStockId())
                .orElseThrow(() -> new EntityNotFound("Estoque não encontrado"));

        ownerStockProduct(product, stock);

        if (inventoryDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Inventory inventory = Inventory.builder()
                .product(product)
                .stock(stock)
                .quantity(inventoryDTO.getQuantity())
                .build();

        inventoryRepository.save(inventory);
    }

    public Inventory consultInventory(Long id) {

        Inventory inventoryEntity = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Inventory não encontrado"));

        isValidInventory(inventoryEntity);

        return inventoryEntity;
    }

    public List<Inventory> consultAllInventory() {
        User user = isSellerOrThrow();

        if (user.getRole() == Role.ADMIN) {
            return inventoryRepository.findAll();
        }

        return inventoryRepository.findByProductUserIdAndStockUserId(user.getId(), user.getId()
        );
    }

    public void updateInventory(Long id, InventoryUpdateDTO inventoryUpdateDTO) {

        Inventory inventoryEntity = consultInventory(id);

        if (inventoryUpdateDTO.getQuantity() != null) {

            if (inventoryUpdateDTO.getQuantity() < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa");
            }

            inventoryEntity.setQuantity(inventoryUpdateDTO.getQuantity());
        }

        inventoryRepository.save(inventoryEntity);
    }

    public void deleteInventory(Long id) {

        Inventory inventoryEntity = consultInventory(id);

        inventoryRepository.deleteById(id);
    }
}