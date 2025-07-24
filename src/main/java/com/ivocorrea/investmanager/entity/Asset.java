package com.ivocorrea.investmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ivocorrea.investmanager.entity.Enum.AssetTypeEnum;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID assetId;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetTypeEnum type;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "current_price", nullable = false)
    private double currentPrice;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonBackReference
    private Portfolio portfolio;

    public Asset() {
    }

    public Asset(String ticker, AssetTypeEnum type, int quantity, double currentPrice, Portfolio portfolio) {
        this.ticker = ticker;
        this.type = type;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.portfolio = portfolio;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public AssetTypeEnum getType() {
        return type;
    }

    public void setType(AssetTypeEnum type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
