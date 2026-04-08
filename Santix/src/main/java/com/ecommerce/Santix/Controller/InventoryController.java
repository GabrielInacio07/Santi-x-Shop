package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Inventory.InventoryDTO;
import com.ecommerce.Santix.DTOs.Inventory.InventoryReponseDTO;
import com.ecommerce.Santix.DTOs.Inventory.InventoryUpdateDTO;
import com.ecommerce.Santix.model.Inventory;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @PostMapping
    public ResponseEntity<Void> salvarInventory(@RequestBody InventoryDTO inventoryDTO, @AuthenticationPrincipal User user) {

        service.saveInventory(inventoryDTO, user.getId());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryReponseDTO> consultInventory(@PathVariable Long id, @AuthenticationPrincipal User user) {

        Inventory inventory = service.consultInventory(id, user.getId());

        InventoryReponseDTO responseDTO = new InventoryReponseDTO(
                inventory.getId(),
                inventory.getQuantity(),
                inventory.getProduct().getId(),
                inventory.getStock().getId()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<InventoryReponseDTO>> consultAllIventory(@AuthenticationPrincipal User user) {
        List<Inventory> inventorys = service.consultAllInvetory(user.getId());

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
            @AuthenticationPrincipal User user) {

        service.updateInventory(id, inventoryUpdateDTO, user.getId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id, @AuthenticationPrincipal User user){
        service.deleteIventory(id, user.getId());

        return ResponseEntity.noContent().build();
    }
}
