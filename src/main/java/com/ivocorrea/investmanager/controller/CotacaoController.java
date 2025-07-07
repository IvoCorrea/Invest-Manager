package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.service.BrapiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class CotacaoController {

    private final BrapiClient brapiClient;

    public CotacaoController(BrapiClient brapiClient) {
        this.brapiClient = brapiClient;
    }

    @GetMapping("/{ticker}")
    public String getPrice(@PathVariable String ticker) {
        return brapiClient.getPrice(ticker);
    }
}

