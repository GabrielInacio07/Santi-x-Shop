package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Inventory.InventoryDTO;
import com.ecommerce.Santix.DTOs.Inventory.InventoryReponseDTO;
import com.ecommerce.Santix.DTOs.Inventory.InventoryUpdateDTO;
import com.ecommerce.Santix.model.Inventory;
import com.ecommerce.Santix.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @PostMapping
    public ResponseEntity<Void> salvarInventory(@RequestBody InventoryDTO inventoryDTO, @RequestHeader("userId") Long userId) {

        service.saveInventory(inventoryDTO, userId);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryReponseDTO> consultInventory(@PathVariable Long id, @RequestHeader("userId") Long userId) {

        Inventory inventory = service.consultInventory(id, userId);

        InventoryReponseDTO responseDTO = new InventoryReponseDTO(
                inventory.getId(),
                inventory.getQuantity(),
                inventory.getProduct().getId(),
                inventory.getStock().getId()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<InventoryReponseDTO>> consultAllIventory(@RequestHeader("userId") Long userId) {
        List<Inventory> inventorys = service.consultAllInvetory(userId);

        List<InventoryReponseDTO> reponseDTOS = inventorys.stream()
                .map(inventory -> new InventoryReponseDTO(
                        inventory.getId(),
                        inventory.getQuantity(),
                        inventory.getProduct().getId(),
                        inventory.getStock().getId()
                )).toList();

        return ResponseEntity.ok(reponseDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryUpdateDTO inventoryUpdateDTO,
            @RequestHeader("userId") Long userId) {

        service.updateInventory(id, inventoryUpdateDTO, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id, @RequestHeader("userId") Long userId){
        service.deleteIventory(id,userId);

        return ResponseEntity.noContent().build();
    }
}
