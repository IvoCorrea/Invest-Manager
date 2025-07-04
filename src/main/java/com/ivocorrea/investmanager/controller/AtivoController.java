package com.ivocorrea.investmanager.controller;

import com.ivocorrea.investmanager.entity.Ativo;
import com.ivocorrea.investmanager.repository.AtivoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ativos")
public class AtivoController {

    private final AtivoRepository ATIVO_REPOSITORY;

    public AtivoController(AtivoRepository ativoRepository) {
        this.ATIVO_REPOSITORY = ativoRepository;
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<Ativo> getAtivo(@PathVariable String ticker) {
        Ativo ativo = ATIVO_REPOSITORY.findByTicker(ticker);
        if (ticker != null) {
            return ResponseEntity.ok(ativo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Ativo> getAllAtivos(){
        return ATIVO_REPOSITORY.findAll();
    }

    @PostMapping
    public ResponseEntity<Ativo> postAtivo(@RequestBody Ativo ativo) {
        if (ativo != null) {
            Ativo posted = ATIVO_REPOSITORY.save(ativo);
            URI location = URI.create("/ativos/" + posted.getTicker());
            return ResponseEntity.created(location).body(posted);

        } else return ResponseEntity.badRequest().build();
    }

}
