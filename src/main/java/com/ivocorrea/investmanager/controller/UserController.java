package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.dto.CreateUserDto;
import com.ivocorrea.investmanager.dto.PutUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody CreateUserDto userDto) {
        UUID userId = userService.createUser(userDto);
        return ResponseEntity.created(URI.create("/user/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String userId){
        Optional<User> user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Optional<User>> putUser(@PathVariable String userId, @RequestBody PutUserDto userDto) {
        Optional<User> user = userService.putUser(userDto, userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
