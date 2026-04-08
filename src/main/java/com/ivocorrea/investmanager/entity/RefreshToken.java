package com.ivocorrea.investmanager.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime expirationDate;
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RefreshToken(LocalDateTime expirationDate, String token, User user) {
        this.expirationDate = expirationDate;
        this.token = token;
        this.user = user;
    }

    public RefreshToken() {
    }
}
