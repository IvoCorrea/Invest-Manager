package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.user.CreateUserDto;
import com.ivocorrea.investmanager.dto.user.PutUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.exception.UserExceptionHandler;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UUID createUser(CreateUserDto userDto) {
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

    public User getUser(String userId, UUID userLoggedId) {
        if (!UUID.fromString(userId).equals(userLoggedId)) throw new AccessDeniedException("Access Denied");

        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserExceptionHandler.NotFoundException("User not found"));

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User putUser(PutUserDto userDto, String userId, UUID userLoggedId) {
        if (!UUID.fromString(userId).equals(userLoggedId)) throw new AccessDeniedException("Access Denied");

        User ExistingUser = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserExceptionHandler.NotFoundException("User not found"));

        ExistingUser.setUsername(userDto.username());
        ExistingUser.setEmail(userDto.email());

        return userRepository.save(ExistingUser);
    }

    public void deleteUser(String userId, UUID userLoggedId) {
        if (!UUID.fromString(userId).equals(userLoggedId)) throw new AccessDeniedException("Access Denied");
        try {
            userRepository.deleteById(UUID.fromString(userId));
        } catch (RuntimeException e) {
            throw new UserExceptionHandler.NotFoundException("User not found");
        }
    }
}
