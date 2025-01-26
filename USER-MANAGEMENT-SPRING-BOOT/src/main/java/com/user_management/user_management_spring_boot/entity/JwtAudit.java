package com.user_management.user_management_spring_boot.entity;

import java.util.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "jwt_audit")
public class JwtAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for efficiency
    @JoinColumn(name = "user_id", nullable = false) // Reference to the UserInfo table
    private UserInfo user;

    @Column(nullable = false)
    private String token;

    @Column(name = "issued_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime issuedAt = LocalDateTime.now();

    @Column(name = "expiry", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid = true;
}