package com.ecommerce.Santix.DTOs;

import com.ecommerce.Santix.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private String name;
    private String email;
    private Role role;
}
