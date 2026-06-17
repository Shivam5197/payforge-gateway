package com.payforge.gateway.apikey.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID merchantId;

    @Column(nullable = false, unique = true, length = 128)
    private String keyHash;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}