package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Login.LoginRequestDTO;
import com.ecommerce.Santix.DTOs.Register.RegisterRequestDTO;
import com.ecommerce.Santix.repositories.UserRepository;
import com.ecommerce.Santix.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginRequestDTO requestDTO){

        var userNamePassword = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(),requestDTO.getPassword());
        var auth = authenticationManager.authenticate(userNamePassword);

        return ResponseEntity.ok("Login Realizado com sucesso");
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody @Valid RegisterRequestDTO requestDTO){
        service.saveUser(requestDTO);


        return ResponseEntity.status(201).body("Usuário criado com sucesso");
    }

}
