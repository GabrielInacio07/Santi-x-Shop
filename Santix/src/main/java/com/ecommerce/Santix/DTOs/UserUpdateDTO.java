package com.ecommerce.Santix.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private String name;
    private String email;
    private String password;

}