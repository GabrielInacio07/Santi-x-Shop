package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Stock.StockDTO;
import com.ecommerce.Santix.DTOs.Stock.StockReponseDTO;
import com.ecommerce.Santix.DTOs.Stock.StockUpdateDTO;
import com.ecommerce.Santix.model.Stock;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping
    public ResponseEntity<Void> salvarStock(@RequestBody StockDTO stockDTO, @AuthenticationPrincipal User user){
        service.saveStock(stockDTO, user.getId());

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<StockReponseDTO>> buscarAllStocks( @AuthenticationPrincipal User user){

        List<Stock> stocks = service.consultAllStock(user.getId());

        List<StockReponseDTO> response = stocks.stream()
                .map(stock -> new StockReponseDTO(
                        stock.getId(),
                        stock.getLocation(),
                        stock.getUser().getId()
                        )).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockReponseDTO> buscarStock(@PathVariable Long id,  @AuthenticationPrincipal User user){
        Stock stock = service.consultStock(id, user.getId());

        StockReponseDTO responseDTO = new StockReponseDTO(
                stock.getUser().getId(),
                stock.getLocation(),
                stock.getId());

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestBody StockUpdateDTO stockDTO,  @AuthenticationPrincipal User user){

        service.udpateStock(id, stockDTO, user.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id,  @AuthenticationPrincipal User user){
        service.deleteStock(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
