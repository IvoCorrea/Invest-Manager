package com.ivocorrea.investmanager.repository;

import com.ivocorrea.investmanager.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
}
