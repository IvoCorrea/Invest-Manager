package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.dto.asset.AddAssetDTO;
import com.ivocorrea.investmanager.dto.asset.PutAssetDTO;
import com.ivocorrea.investmanager.entity.Asset;
import com.ivocorrea.investmanager.entity.Portfolio;
import com.ivocorrea.investmanager.entity.User;
import com.ivocorrea.investmanager.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    private ResponseEntity<Portfolio> createPortfolio(
            @AuthenticationPrincipal User user
    ) {
        UUID createdPortfolio = portfolioService.createPortfolio(user.getUserid());
        return ResponseEntity.created(URI.create("/portfolio/" + createdPortfolio.toString())).build();
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<Portfolio> getPortfolioById(
            @PathVariable String portfolioId, @AuthenticationPrincipal User user
    ) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId, user.getUserid());
        return ResponseEntity.ok(portfolio);
    }

    @GetMapping
    public ResponseEntity<List<Portfolio>> getAllPortfolios(
            @AuthenticationPrincipal User user
    ) {
        List<Portfolio> portfolioList = portfolioService.getAllPortfolios(user.getUserid());
        return ResponseEntity.ok(portfolioList);
    }

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(
            @PathVariable String portfolioId,
            @AuthenticationPrincipal User user
    ) {
        portfolioService.deletePortfolio(portfolioId, user.getUserid());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{portfolioId}/asset")
    public ResponseEntity<Portfolio> addAssetToPortfolio(
            @RequestBody AddAssetDTO addAssetDTO,
            @PathVariable String portfolioId,
            @AuthenticationPrincipal User user
    ) {
        Portfolio portfolio = portfolioService.addAssetToPortfolio(addAssetDTO, portfolioId, user.getUserid());
        return ResponseEntity.ok(portfolio);
    }

    @PatchMapping("/{portfolioId}/asset/{assetId}")
    public ResponseEntity<Asset> updateAsset(
            @RequestBody PutAssetDTO putAssetDTO,
            @PathVariable String portfolioId,
            @PathVariable String assetId,
            @AuthenticationPrincipal User user
    ) {
        Asset asset = portfolioService.updateAsset(putAssetDTO, portfolioId, assetId, user.getUserid());
        return ResponseEntity.ok(asset);
    }

    @DeleteMapping("/{portfolioId}/asset/{assetId}")
    public ResponseEntity<Void> deleteAssetInPortfolio(
            @PathVariable String portfolioId,
            @PathVariable String assetId,
            @AuthenticationPrincipal User user
    ) {
        portfolioService.deleteAssetInPortfolio(portfolioId, assetId, user.getUserid());
        return ResponseEntity.noContent().build();
    }
}
