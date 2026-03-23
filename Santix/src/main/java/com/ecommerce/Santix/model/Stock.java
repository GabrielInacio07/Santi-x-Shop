package com.ecommerce.Santix.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @Column(nullable = false)
    private String location;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastUpdate;


    @OneToOne(mappedBy = "stock")
    private Product product;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}