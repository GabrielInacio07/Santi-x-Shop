package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.User.UserDTO;
import com.ecommerce.Santix.DTOs.User.UserResponseDTO;
import com.ecommerce.Santix.DTOs.User.UserUpdateDTO;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUser(@PathVariable Long id, @RequestBody UserUpdateDTO userDTO){

        service.updateUser(id, userDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarUsuario(@PathVariable Long id){
        User user = service.consultUser(id);

        UserResponseDTO responseDTO = new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> buscarAllUsuario(){

        List<User> users = service.consultAllUser();

        List<UserResponseDTO> response = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
