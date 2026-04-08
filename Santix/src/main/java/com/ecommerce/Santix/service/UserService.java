package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Auth.RegisterRequestDTO;
import com.ecommerce.Santix.DTOs.User.UserUpdateDTO;
import com.ecommerce.Santix.Exception.EntityNotFound;
import com.ecommerce.Santix.Exception.UnauthorizedException;
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


    private void validateUser(RegisterRequestDTO requestDTO) {

        if (repository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new UnauthorizedException("Usuário já existe");
        }

        if (requestDTO.getName() == null || requestDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (requestDTO.getPassword() == null || requestDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }

    }

    public void saveUser(RegisterRequestDTO requestDTO) {
        validateUser(requestDTO);

        User user = User.builder()
                .name(requestDTO.getName().trim())
                .email(requestDTO.getEmail().trim())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(requestDTO.getRole() == null ? Role.CUSTOMER : requestDTO.getRole())
                .build();

        repository.save(user);
    }

        public User consultUser(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFound("Usuário não encontrado")
                );
    }

    public List<User> consultAllUser() {
        return repository.findAll();
    }

    public User consultEmailUser(String email) {
        return repository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFound("Usuário não encontrado para o email informado")
                );
    }

    public void deleteUser(Long id) {

        User user = consultUser(id);
        repository.delete(user);
    }

    public void updateUser(Long id, UserUpdateDTO userDTO) {
        User userEntity = consultUser(id);

        if (userDTO.getName() != null) {
            userEntity.setName(userDTO.getName());
        }

        if (userDTO.getEmail() != null) {
            userEntity.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        repository.save(userEntity);

    }
}
