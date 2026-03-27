package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Stock.StockDTO;
import com.ecommerce.Santix.DTOs.Stock.StockReponseDTO;
import com.ecommerce.Santix.model.Stock;
import com.ecommerce.Santix.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping
    public ResponseEntity<Void> salvarStock(@RequestBody StockDTO stockDTO){
        service.saveStock(stockDTO);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<StockReponseDTO>> buscarAllStocks(){

        List<Stock> stocks = service.consultAllStock();

        List<StockReponseDTO> response = stocks.stream()
                .map(stock -> new StockReponseDTO(
                        stock.getId(),
                        stock.getLocation()
                        )).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockReponseDTO> buscarStock(@PathVariable Long id){
        Stock stock = service.consultStock(id);

        StockReponseDTO responseDTO = new StockReponseDTO(stock.getId(), stock.getLocation());

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable Long id,@RequestBody StockReponseDTO stockDTO){
        service.udpateStock(id,stockDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id){
        service.deleteStock(id);

        return ResponseEntity.noContent().build();
    }
}
