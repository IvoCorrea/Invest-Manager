package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.controller.dto.CreateUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto userDto) {

            // DTO => Entity
            var Entity = new User(
                    userDto.username(),
                    userDto.email() ,
                    userDto.password(),
                    Instant.now(),
                    null);

            User postedUser = userRepository.save(Entity);
            return postedUser.getUserid();
    }

    public Optional<User> getUser(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }
}
