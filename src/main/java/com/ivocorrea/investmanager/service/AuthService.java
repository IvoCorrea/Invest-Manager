package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.login.LoginRequestDTO;
import com.ivocorrea.investmanager.dto.login.LoginResponseDTO;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.exception.UserExceptionHandler;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserExceptionHandler.NotFoundException("User not Found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }

        String acessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponseDTO(acessToken, refreshToken);
    }

    public LoginResponseDTO authRefresh(String token) {
        if (!refreshTokenService.isRefreshTokenValid(token)) throw new RuntimeException("Token not valid");

        String acessToken = jwtService.generateToken(refreshTokenService.findbyToken(token).getUser());
        String rotatedRefreshToken = refreshTokenService.rotateRefreshToken(token);


        return new LoginResponseDTO(acessToken, rotatedRefreshToken);
    }
}
