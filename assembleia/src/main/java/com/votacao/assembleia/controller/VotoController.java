package com.votacao.assembleia.controller;

import com.votacao.assembleia.dto.VotoRequestDTO;
import com.votacao.assembleia.model.Voto;
import com.votacao.assembleia.service.VotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pautas/{pautaId}/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<Voto> votar(
            @PathVariable Long pautaId,
            @RequestBody VotoRequestDTO request) {
        return ResponseEntity.ok(votoService.registrarVoto(pautaId, request));
    }
}
