package com.ivocorrea.investmanager.repository;

import com.ivocorrea.investmanager.entity.Portfolio;
import com.ivocorrea.investmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    List<Portfolio> findAllByUser(User user);
}
