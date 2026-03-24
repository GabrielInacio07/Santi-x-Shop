package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.UserDTO;
import com.ecommerce.Santix.DTOs.UserUpdateDTO;
import com.ecommerce.Santix.Exception.UserNotFoundException;
import com.ecommerce.Santix.model.Role;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public void saveUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(
                        userDTO.getRole() == Role.SELLER
                        ? Role.SELLER
                        : Role.CUSTOMER
                )
                .build();
        repository.save(user);
    }

    public User consultUser(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("Usuário não encontrado para o ID informado")
                );
    }

    public List<User> consultAllUser() {
        return repository.findAll();
    }

    public User consultEmailUser(String email) {
        return repository.findByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("Usuário não encontrado para o email informado")
                );
    }

    public void deleteUser(Long id){

        User user = consultUser(id);
        repository.delete(user);
    }

    public void updateUser(Long id, UserUpdateDTO userDTO){
        User userEntity = consultUser(id);

        if(userDTO.getName() != null){
            userEntity.setName(userDTO.getName());
        }

        if(userDTO.getEmail() != null){
            userEntity.setEmail(userDTO.getEmail());
        }

        if(userDTO.getPassword() != null){
            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        repository.save(userEntity);

    }
}
