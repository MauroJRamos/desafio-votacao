package com.votacao.assembleia.controller;

import com.votacao.assembleia.dto.PautaRequestDTO;
import com.votacao.assembleia.dto.ResultadoVotacaoDTO;
import com.votacao.assembleia.model.Pauta;
import com.votacao.assembleia.service.PautaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody PautaRequestDTO request) {
        return ResponseEntity.ok(pautaService.criarPauta(request));
    }

    @PostMapping("/{id}/sessao")
    public ResponseEntity<Pauta> abrirSessao(
            @PathVariable Long id,
            @RequestParam(required = false) Integer duracaoMinutos) {
        return ResponseEntity.ok(pautaService.abrirSessao(id, duracaoMinutos));
    }

    @GetMapping("/{id}/resultado")
    public ResponseEntity<ResultadoVotacaoDTO> resultado(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.contabilizarVotos(id));
    }
}
