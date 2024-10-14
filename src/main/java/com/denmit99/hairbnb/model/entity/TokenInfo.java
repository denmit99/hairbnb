package com.denmit99.hairbnb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "token")
    private String token;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expired")
    private boolean isExpired;

    @Column(name = "revoked")
    private boolean isRevoked;

}
