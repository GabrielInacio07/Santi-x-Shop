package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Stock.StockDTO;
import com.ecommerce.Santix.DTOs.Stock.StockUpdateDTO;
import com.ecommerce.Santix.Exception.EntityNotFound;
import com.ecommerce.Santix.Exception.UnauthorizedException;
import com.ecommerce.Santix.model.Role;
import com.ecommerce.Santix.model.Stock;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {

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

    private void stockOwner(Stock stock, User user) {

        if (user.getRole() == Role.ADMIN) return;


        if (!stock.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este estoque");
        }
    }

    public void saveStock(StockDTO stockDTO) {
        User user = isSellerOrThrow();

        if (stockDTO.getLocation() == null || stockDTO.getLocation().isBlank()) {
            throw new IllegalArgumentException("Local de estoque não pode estar vazio");
        }

        Stock stock = Stock.builder()
                .user(user)
                .location(stockDTO.getLocation().trim())
                .build();

        stockRepository.save(stock);
    }

    public List<Stock> consultAllStock() {
        User user = isSellerOrThrow();
        return stockRepository.findByUserId(user.getId());
    }

    public Stock consultStock(Long id) {
        User user = isSellerOrThrow();

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Stock não encontrado"));

        stockOwner(stock, user);

        return stock;
    }

    public void updateStock(Long id, StockUpdateDTO stockDTO) {
        User user = isSellerOrThrow();

        Stock stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Stock não encontrado"));

        stockOwner(stockEntity, user);

        if (stockDTO.getLocation() != null && !stockDTO.getLocation().isBlank()) {
            stockEntity.setLocation(stockDTO.getLocation());
        }


        stockRepository.save(stockEntity);
    }

    public void deleteStock(Long id) {
        User user = isSellerOrThrow();

        Stock stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Stock não encontrado"));

        stockOwner(stockEntity, user);

        stockRepository.deleteById(id);
    }
}