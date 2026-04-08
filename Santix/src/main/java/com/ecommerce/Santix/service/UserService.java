package com.ecommerce.Santix.service;

import com.ecommerce.Santix.DTOs.Auth.RegisterRequestDTO;
import com.ecommerce.Santix.DTOs.User.UserUpdateDTO;
import com.ecommerce.Santix.Exception.EntityNotFound;
import com.ecommerce.Santix.Exception.UnauthorizedException;
import com.ecommerce.Santix.model.Role;
import com.ecommerce.Santix.model.User;
import com.ecommerce.Santix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    // 🔐 valida ADMIN
    public void isAdminOrThrow() {
        User user = getAuthenticatedUser();

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Apenas ADMIN pode realizar a ação!");
        }
    }


    private void validateUser(RegisterRequestDTO dto) {

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UnauthorizedException("Usuário já existe");
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
    }

    private void createUser(RegisterRequestDTO dto, Role role) {
        validateUser(dto);

        User user = User.builder()
                .name(dto.getName().trim())
                .email(dto.getEmail().trim())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build();

        repository.save(user);
    }

    public void registerCustomer(RegisterRequestDTO dto) {
        createUser(dto, Role.CUSTOMER);
    }

    public void registerSeller(RegisterRequestDTO dto) {
        isAdminOrThrow();
        createUser(dto, Role.SELLER);
    }


    public User consultUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Usuário não encontrado"));
    }

    public List<User> consultAllUser() {
        return repository.findAll();
    }

    public void deleteUser(Long id) {
        isAdminOrThrow();
        User user = consultUser(id);
        repository.delete(user);
    }

    public void updateUser(Long id, UserUpdateDTO dto) {
        isAdminOrThrow();

        User user = consultUser(id);

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        repository.save(user);
    }
}