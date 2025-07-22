package com.ivocorrea.investmanager.entity;

import com.ivocorrea.investmanager.entity.Enum.AssetTypeEnum;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID assetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetTypeEnum type;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "current_price", nullable = false)
    private double currentPrice;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    public Asset(String name, AssetTypeEnum type, int quantity, double currentPrice, Portfolio portfolio) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
