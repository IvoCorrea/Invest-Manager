package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.LoginRequestDTO;
import com.ivocorrea.investmanager.dto.LoginResponseDTO;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        System.out.println("Email recebido no login: " + request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not Found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token);
    }
}
