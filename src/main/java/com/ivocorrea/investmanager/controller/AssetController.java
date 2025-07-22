package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.entity.Asset;
import com.ivocorrea.investmanager.entity.Portfolio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @PutMapping("/{assetId}")
    public ResponseEntity<Asset> putAsset(@PathVariable String assetId) {
        return null;
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<Asset> deleteAsset(@PathVariable String assetId) {
        return null;
    }
}

