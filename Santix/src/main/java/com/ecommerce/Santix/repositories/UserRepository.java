package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
