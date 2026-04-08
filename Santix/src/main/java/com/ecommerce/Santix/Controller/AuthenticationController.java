package com.ecommerce.Santix.Controller;

import com.ecommerce.Santix.DTOs.Auth.LoginReponseDTO;
import com.ecommerce.Santix.DTOs.Auth.LoginRequestDTO;
import com.ecommerce.Santix.DTOs.Auth.RegisterRequestDTO;
import com.ecommerce.Santix.Security.TokenService;
import com.ecommerce.Santix.model.User;
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
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginReponseDTO> userLogin(@RequestBody LoginRequestDTO requestDTO){

        var userNamePassword = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(),requestDTO.getPassword());
        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginReponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> customerRegister(@RequestBody @Valid RegisterRequestDTO requestDTO){
        service.saveUser(requestDTO);

        return ResponseEntity.status(201).body("Usuário[Customer] criado com sucesso");
    }

    @PostMapping("/register/seller")
    public ResponseEntity<String> SellerRegister(@RequestBody @Valid RegisterRequestDTO requestDTO){
        service.saveUser(requestDTO);

        return ResponseEntity.status(201).body("Usuário[SELLER] criado com sucesso");
    }

}
