package com.ecommerce.Santix.DTOs.User;

import com.ecommerce.Santix.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private Role role;

}