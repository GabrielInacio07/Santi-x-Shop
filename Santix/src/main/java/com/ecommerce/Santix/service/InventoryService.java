package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Inventory.InventoryDTO;
import com.ecommerce.Santix.DTOs.Inventory.InventoryUpdateDTO;
import com.ecommerce.Santix.Exception.InventoryNotFoundException;
import com.ecommerce.Santix.Exception.UserNotFoundException;
import com.ecommerce.Santix.model.*;
import com.ecommerce.Santix.repositories.InventoryRepository;
import com.ecommerce.Santix.repositories.ProductRepository;
import com.ecommerce.Santix.repositories.StockRepository;
import com.ecommerce.Santix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;


    private User isSellerOrThrow(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        if (user.getRole() != Role.SELLER) {
            throw new IllegalArgumentException("Apenas SELLER pode realizar essa operação");
        }

        return user;
    }

    private void ownerStockProduct(Product product, Stock stock, Long userId) {

        if (!product.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Produto não pertence ao usuário");
        }

        if (!stock.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Produto não pertence ao usuário");
        }
    }


    private void isValidIventory(Inventory inventory, Long userId) {

        if (!inventory.getProduct().getUser().getId().equals(userId) ||
                !inventory.getStock().getUser().getId().equals(userId)) {

            throw new IllegalArgumentException("Você não tem permissão para acessar este inventário");
        }
    }

    public void saveInventory(InventoryDTO inventoryDTO, Long userId) {

        User user = isSellerOrThrow(userId);

        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Stock stock = stockRepository.findById(inventoryDTO.getStockId())
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        ownerStockProduct(product, stock, userId);

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

    public Inventory consultInventory(Long id, Long userId) {

        isSellerOrThrow(userId);

        Inventory inventoryEntity = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory não encontrado"));

        isValidIventory(inventoryEntity, userId);

        return inventoryEntity;
    }

    public List<Inventory> consultAllInvetory(Long userId) {
        isSellerOrThrow(userId);
        return inventoryRepository.findByProductUserIdAndStockUserId(userId, userId);
    }

    public void updateInventory(Long id, InventoryUpdateDTO inventoryUpdateDTO, Long userId) {

        Inventory inventoryEntity = consultInventory(id, userId);
        isValidIventory(inventoryEntity, userId);

        if (inventoryUpdateDTO.getQuantity() != null) {

            if (inventoryUpdateDTO.getQuantity() < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa");
            }

            inventoryEntity.setQuantity(inventoryUpdateDTO.getQuantity());
        }
        inventoryRepository.save(inventoryEntity);
    }

    public void deleteIventory(Long id, Long userId) {
        isSellerOrThrow(userId);
        Inventory inventoryEntity = consultInventory(id, userId);

        inventoryRepository.deleteById(id);
    }
}
