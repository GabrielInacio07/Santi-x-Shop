package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Stock.StockDTO;
import com.ecommerce.Santix.DTOs.Stock.StockReponseDTO;
import com.ecommerce.Santix.DTOs.Stock.StockUpdateDTO;
import com.ecommerce.Santix.model.Stock;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.UserRepository;
import com.ecommerce.Santix.service.StockService;
import com.ecommerce.Santix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping
    public ResponseEntity<Void> salvarStock(@RequestBody StockDTO stockDTO,  @RequestHeader("userId") Long userId){
        service.saveStock(stockDTO, userId);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<StockReponseDTO>> buscarAllStocks(@RequestHeader("userId") Long userId){

        List<Stock> stocks = service.consultAllStock(userId);

        List<StockReponseDTO> response = stocks.stream()
                .map(stock -> new StockReponseDTO(
                        stock.getId(),
                        stock.getLocation(),
                        stock.getUser().getId()
                        )).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockReponseDTO> buscarStock(@PathVariable Long id, @RequestHeader("userId") Long userId){
        Stock stock = service.consultStock(id, userId);

        StockReponseDTO responseDTO = new StockReponseDTO(
                stock.getUser().getId(),
                stock.getLocation(),
                stock.getId());

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestBody StockUpdateDTO stockDTO, @RequestHeader("userId") Long userId){

        service.udpateStock(id, stockDTO, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id, @RequestHeader("userId") Long userId){
        service.deleteStock(id, userId);
        return ResponseEntity.noContent().build();
    }
}
