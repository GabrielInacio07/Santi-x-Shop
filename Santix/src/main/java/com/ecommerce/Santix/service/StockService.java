package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Stock.StockDTO;
import com.ecommerce.Santix.DTOs.Stock.StockReponseDTO;
import com.ecommerce.Santix.DTOs.Stock.StockUpdateDTO;
import com.ecommerce.Santix.Exception.StockNotFoundException;
import com.ecommerce.Santix.Exception.UserNotFoundException;
import com.ecommerce.Santix.model.Role;
import com.ecommerce.Santix.model.Stock;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.StockRepository;
import com.ecommerce.Santix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {

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

    private void stockOwner(Stock stock, Long userId) {
        if (!stock.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Você não tem permissão para acessar este estoque");
        }
    }

    public void saveStock(StockDTO stockDTO, Long userId) {
        User user = isSellerOrThrow(userId);

        if (stockDTO.getLocation() == null || stockDTO.getLocation().isBlank()) {
            throw new IllegalArgumentException("Local de estoque não pode estar vazio");
        }

        Stock stock = Stock.builder()
                .user(user)
                .location(stockDTO.getLocation().trim())
                .build();

        stockRepository.save(stock);
    }

    public List<Stock> consultAllStock(Long userId) {
        isSellerOrThrow(userId);
        return stockRepository.findByUserId(userId);
    }

    public Stock consultStock(Long id, Long userId) {
        isSellerOrThrow(userId);
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock não encontrado"));

        stockOwner(stock, userId);

        return stock;
    }

    public void udpateStock(Long id, StockUpdateDTO stockDTO, Long userId) {

        Stock stockEntity = consultStock(id, userId);

        stockOwner(stockEntity, userId);

        if (stockDTO.getLocation() != null && !stockDTO.getLocation().isBlank()) {
            stockEntity.setLocation(stockDTO.getLocation());
        }

        stockRepository.save(stockEntity);
    }

    public void deleteStock(Long id, Long userId) {

        Stock stockEntity = consultStock(id, userId);

        stockOwner(stockEntity, userId);

        stockRepository.deleteById(id);
    }
}




