package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.controller.dto.CreateUserDto;
import com.ivocorrea.investmanager.controller.dto.PutUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto userDto) {

        // DTO => Entity
        var EntityUser = new User(
                userDto.username(),
                userDto.email(),
                userDto.password(),
                Instant.now(),
                null);

        User postedUser = userRepository.save(EntityUser);
        return postedUser.getUserid();
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public Optional<User> putUser(PutUserDto userDto, String userId) {

        Optional<User> ExistingUser = userRepository.findById(UUID.fromString(userId));
        if (ExistingUser.isEmpty()) {
            return Optional.empty();
        }

        // Get = Optional
        User userUpdated = ExistingUser.get();
        userUpdated.setUsername(userDto.username());
        userUpdated.setEmail(userDto.email());

        return Optional.of(userRepository.save(userUpdated));
    }

    public void deleteUser(String userId) {
        Optional<User> ExistingUser = userRepository.findById(UUID.fromString(userId));
        if (ExistingUser.isEmpty()) {
            throw new RuntimeException();
        }
        userRepository.deleteById(UUID.fromString(userId));

    }

}
