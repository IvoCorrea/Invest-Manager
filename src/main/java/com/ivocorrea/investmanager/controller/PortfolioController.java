package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.entity.Asset;
import com.ivocorrea.investmanager.entity.Portfolio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @PostMapping
    private ResponseEntity<Portfolio> createPortfolio(@RequestBody String portfolio) {
        return null;
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable String portfolioId) {
        return null;
    }

    @PostMapping("/portfolio/{assetId}/asset")
    public ResponseEntity<Portfolio> addAssetToPortfolio(@PathVariable String portfolioId) {
        return null;
    }

    @GetMapping("/portfolio/{assetId}/asset")
    public ResponseEntity<Asset> getAsset(@PathVariable String assetId) {
        return null;
    }

}
