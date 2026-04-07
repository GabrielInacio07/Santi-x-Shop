package com.ecommerce.Santix.DTOs.Register;

import com.ecommerce.Santix.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
}
