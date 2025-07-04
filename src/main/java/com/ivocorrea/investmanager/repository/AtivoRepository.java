package com.ivocorrea.investmanager.repository;

import com.ivocorrea.investmanager.entity.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtivoRepository extends JpaRepository<Ativo, Long> {
    Ativo findByTicker(String ticker);
}
