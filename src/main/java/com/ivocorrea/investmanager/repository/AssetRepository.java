package com.ivocorrea.investmanager.repository;

import com.ivocorrea.investmanager.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
}
