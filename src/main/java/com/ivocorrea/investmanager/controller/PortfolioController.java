package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.dto.AddAssetDTO;
import com.ivocorrea.investmanager.dto.CreatePortfolioDTO;
import com.ivocorrea.investmanager.entity.Asset;
import com.ivocorrea.investmanager.entity.Portfolio;
import com.ivocorrea.investmanager.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    private ResponseEntity<Portfolio> createPortfolio(@RequestBody CreatePortfolioDTO portfolioDTO) {
        UUID createdPortfolio = portfolioService.createPortfolio(portfolioDTO);
        return ResponseEntity.created(URI.create("/portfolio/" + createdPortfolio.toString())).build();
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<Optional<Portfolio>> getPortfolioById(@PathVariable String portfolioId) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolioById(portfolioId);
        return ResponseEntity.ok(portfolio);
    }

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable String portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{portfolioId}/asset")
    public ResponseEntity<Portfolio> addAssetToPortfolio(
            @RequestBody AddAssetDTO addAssetDTO,
            @PathVariable String portfolioId
    ) {
        Portfolio portfolio = portfolioService.addAssetToPortfolio(addAssetDTO, portfolioId);
        return ResponseEntity.ok(portfolio);
    }

    @DeleteMapping("/{portfolioId}/asset/{assetId}")
    public ResponseEntity<Void> deleteAssetInPortfolio(
            @PathVariable String portfolioId,
            @PathVariable String assetId
    ) {
        portfolioService.deleteAssetInPortfolio(portfolioId, assetId);
        return ResponseEntity.noContent().build();
    }
}
