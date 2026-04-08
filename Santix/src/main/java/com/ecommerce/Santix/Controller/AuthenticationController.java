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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginReponseDTO> login(@RequestBody LoginRequestDTO dto) {

        var authToken = new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()
        );

        var auth = authenticationManager.authenticate(authToken);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginReponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody @Valid RegisterRequestDTO dto) {
        service.registerCustomer(dto);
        return ResponseEntity.status(201).body("Customer criado com sucesso");
    }

    @PostMapping("/register/seller")
    public ResponseEntity<String> registerSeller(@RequestBody @Valid RegisterRequestDTO dto) {
        service.registerSeller(dto);
        return ResponseEntity.status(201).body("Seller criado com sucesso");
    }
}