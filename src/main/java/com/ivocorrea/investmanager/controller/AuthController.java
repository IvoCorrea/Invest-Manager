package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.dto.user.CreateUserDto;
import com.ivocorrea.investmanager.dto.login.LoginRequestDTO;
import com.ivocorrea.investmanager.dto.login.LoginResponseDTO;
import com.ivocorrea.investmanager.dto.RefreshRequestDTO;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.service.AuthService;
import com.ivocorrea.investmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody CreateUserDto userDto) {
        UUID userId = userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/user/" + userId.toString())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginAuth(@RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshAuth(@RequestBody RefreshRequestDTO requestDTO){
        return ResponseEntity.ok(authService.authRefresh(requestDTO.refreshToken()));
    }
}
