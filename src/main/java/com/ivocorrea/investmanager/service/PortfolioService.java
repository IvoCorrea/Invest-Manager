package com.ivocorrea.investmanager.service;

import com.ivocorrea.investmanager.dto.AddAssetDTO;
import com.ivocorrea.investmanager.dto.CreatePortfolioDTO;
import com.ivocorrea.investmanager.dto.PutAssetDTO;
import com.ivocorrea.investmanager.entity.Asset;
import com.ivocorrea.investmanager.entity.Portfolio;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.repository.AssetRepository;
import com.ivocorrea.investmanager.repository.PortfolioRepository;
import com.ivocorrea.investmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository, AssetRepository assetRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
    }

    public Portfolio getPortfolioById(String portfolioId) {
        return portfolioRepository.findById(UUID.fromString(portfolioId))
                .orElseThrow(() -> new RuntimeException("Portfolio Not Found"));
    }

    public UUID createPortfolio(CreatePortfolioDTO portfolioDTO) {

        User user = userRepository.findById(portfolioDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Portfolio portfolioEntity = new Portfolio();
        portfolioEntity.setUser(user);
        portfolioEntity.setAssets(new ArrayList<>());
        portfolioEntity.setCreatedAt(Instant.now());

        Portfolio portfolioSaved = portfolioRepository.save(portfolioEntity);

        return portfolioSaved.getPortfolioId();
    }

    public Portfolio addAssetToPortfolio(AddAssetDTO assetDTO, String portfolioId) {

        Portfolio portfolioToBePut = portfolioRepository.findById(UUID.fromString(portfolioId))
                .orElseThrow(() -> new RuntimeException("Portfolio nao encontrado"));

        Asset newAsset = new Asset();
        newAsset.setTicker(assetDTO.ticker());
        newAsset.setType(assetDTO.type());
        newAsset.setQuantity(assetDTO.quantity());
        newAsset.setCurrentPrice(assetDTO.currentPrice());
        newAsset.setPortfolio(portfolioToBePut);

        portfolioToBePut.addNewAsset(newAsset);

        return portfolioRepository.save(portfolioToBePut);
    }

    public Asset updateAsset(PutAssetDTO putAssetDTO, String portfolioId, String assetId) {
        Asset assetToBeUpdated = assetRepository.findById(UUID.fromString(assetId))
                .orElseThrow(() -> new RuntimeException("Asset Not Found"));

        if (!assetToBeUpdated.getPortfolio().getPortfolioId().equals(UUID.fromString(portfolioId))) {
            throw new IllegalArgumentException("Asset does not belong to the specified portfolio");
        }

        try {
            assetToBeUpdated.setQuantity(putAssetDTO.quantity());
            assetToBeUpdated.setCurrentPrice(putAssetDTO.currentPrice());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal Arguments");
        }

        return assetRepository.save(assetToBeUpdated);
    }

    public void deletePortfolio(String portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(UUID.fromString(portfolioId))
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not Found"));
        portfolioRepository.deleteById(portfolio.getPortfolioId());
    }

    public void deleteAssetInPortfolio(String portfolioId, String assetId) {
        Portfolio portfolio = portfolioRepository.findById(UUID.fromString(portfolioId))
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not Found"));

        Asset asset = assetRepository.findById(UUID.fromString(assetId))
                .orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        if (!asset.getPortfolio().getPortfolioId().equals(portfolio.getPortfolioId())) {
            throw new IllegalArgumentException("Asset does not belong to the specified portfolio");
        }

        assetRepository.deleteById(asset.getAssetId());
    }
}
