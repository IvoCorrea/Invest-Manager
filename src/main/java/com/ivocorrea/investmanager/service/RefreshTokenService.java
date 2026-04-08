package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.LoginResponseDTO;
import com.ivocorrea.investmanager.entity.RefreshToken;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.repository.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public String createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpirationDate(LocalDateTime.now().plusDays(30));

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    public boolean isRefreshTokenValid(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        return refreshToken.getExpirationDate().isAfter(LocalDateTime.now());
    }

    public String rotateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));

        String rotateRefreshToken = UUID.randomUUID().toString();
        refreshToken.setToken(rotateRefreshToken);

        refreshTokenRepository.save(refreshToken);
        return rotateRefreshToken;
    }

    public RefreshToken findbyToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
    }
}
