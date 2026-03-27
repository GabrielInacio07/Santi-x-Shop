package com.ecommerce.Santix.service;
import com.ecommerce.Santix.DTOs.Stock.StockDTO;
import com.ecommerce.Santix.DTOs.Stock.StockReponseDTO;
import com.ecommerce.Santix.Exception.StockNotFoundException;
import com.ecommerce.Santix.model.Stock;
import com.ecommerce.Santix.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    public void saveStock(StockDTO stockDTO){

        if (stockDTO.getLocation() == null || stockDTO.getLocation().isBlank()) {
            throw new IllegalArgumentException("Local de estoque não pode estar vazio");
        }

        Stock stock = Stock.builder()
                .location(stockDTO.getLocation())
                .build();

        stockRepository.save(stock);
    }

    public List<Stock> consultAllStock(){
        return stockRepository.findAll();
    }

    public Stock consultStock(Long id){
        return stockRepository.findById(id).orElseThrow(
                () -> new StockNotFoundException("Stock não encontrado para o ID informado")
        );
    }

    public void udpateStock(Long id, StockReponseDTO stockDTO){
        Stock stockEntity = consultStock(id);

        if (stockDTO.getLocation() != null && !stockDTO.getLocation().isBlank()) {
            stockEntity.setLocation(stockDTO.getLocation());
        }

        stockRepository.save(stockEntity);
    }

    public void deleteStock(Long id){
        if (!stockRepository.existsById(id)) {
            throw new StockNotFoundException("Stock não encontrado para o ID informado");
        }

        stockRepository.deleteById(id);
    }
}
