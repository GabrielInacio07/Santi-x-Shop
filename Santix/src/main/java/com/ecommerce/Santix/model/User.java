package com.ecommerce.Santix.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column (unique = true)
    private String email;

    @Column (nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column (updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    //Quando insere no Banco
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    //Quando atualiza no Banco
    @PreUpdate
    public void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }

}
