package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);


}
