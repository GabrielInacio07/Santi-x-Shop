package com.ecommerce.Santix.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDTO {

    private String email;
    private String password;
}
