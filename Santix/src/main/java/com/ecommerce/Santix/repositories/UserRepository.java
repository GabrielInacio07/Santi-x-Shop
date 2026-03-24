package com.ecommerce.Santix.repositories;

import com.ecommerce.Santix.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


//    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);


}
