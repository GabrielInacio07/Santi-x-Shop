package com.ecommerce.Santix.DTOs;

import com.ecommerce.Santix.model.Role;
import lombok.Getter;

@Getter
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private Role role;

}