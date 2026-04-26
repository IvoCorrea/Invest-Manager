package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.login.LoginRequestDTO;
import com.ivocorrea.investmanager.dto.login.LoginResponseDTO;
import com.ivocorrea.investmanager.dto.user.CreateUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.exception.UserExceptionHandler;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

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

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    public LoginResponseDTO authRefresh(String token) {
        if (!refreshTokenService.isRefreshTokenValid(token)) throw new RuntimeException("Token not valid");

        String accessToken = jwtService.generateToken(refreshTokenService.findbyToken(token).getUser());
        String rotatedRefreshToken = refreshTokenService.rotateRefreshToken(token);


        return new LoginResponseDTO(accessToken, rotatedRefreshToken);
    }

    public UUID register(CreateUserDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new IllegalArgumentException("Email Already Registered");
        }
        if (userRepository.existsByUsername(userDto.username())) {
            throw new IllegalArgumentException("Username Already Registered");
        }

        try {
            // DTO → Entity
            var EntityUser = new User(
                    userDto.username(),
                    userDto.email(),
                    passwordEncoder.encode(userDto.password()),
                    Instant.now(),
                    null);

            User postedUser = userRepository.save(EntityUser);
            return postedUser.getUserid();
        } catch (RuntimeException e) {
            throw new UserExceptionHandler.NotFoundException("User not found");
        }
    }
}
