package com.ecommerce.Santix.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {

    private String name;
    private String email;
    private String password;
}
