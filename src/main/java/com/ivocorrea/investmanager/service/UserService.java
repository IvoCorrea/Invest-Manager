package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.user.PutUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.exception.UserExceptionHandler;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
