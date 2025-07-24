package com.ivocorrea.investmanager.dto;

import com.ivocorrea.investmanager.entity.Enum.AssetTypeEnum;

public record AddAssetDTO(String ticker, AssetTypeEnum type, int quantity, double currentPrice) {
}
