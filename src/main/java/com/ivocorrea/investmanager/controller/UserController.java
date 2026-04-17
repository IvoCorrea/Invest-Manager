package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.dto.user.PutUserDto;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(
            @PathVariable String userId,
            @AuthenticationPrincipal User userLogged
    ){
        User user = userService.getUser(userId, userLogged.getUserid());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> putUser(
            @PathVariable String userId,
            @RequestBody PutUserDto userDto,
            @AuthenticationPrincipal User userLogged
    ) {
        User user = userService.putUser(userDto, userId, userLogged.getUserid());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String userId,
            @AuthenticationPrincipal User userLogged
    ) {
        userService.deleteUser(userId, userLogged.getUserid());
        return ResponseEntity.noContent().build();
    }
}
