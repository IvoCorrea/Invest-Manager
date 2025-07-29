package com.ivocorrea.investmanager.repository;

import com.ivocorrea.investmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
