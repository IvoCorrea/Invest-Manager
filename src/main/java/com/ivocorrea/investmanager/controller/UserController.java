package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.controller.dto.CreateUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<User> getUser(@PathVariable String userId){
        return null;
    }
}
