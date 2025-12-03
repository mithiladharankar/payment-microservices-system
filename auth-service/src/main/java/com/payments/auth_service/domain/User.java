package com.payments.auth_service.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // encoded

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String roles; // e.g. "ROLE_USER"

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    // getters and setters
}
