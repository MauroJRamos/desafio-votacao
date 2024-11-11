package com.votacao.assembleia.controller;

import com.votacao.assembleia.dto.VotoRequestDTO;
import com.votacao.assembleia.model.Voto;
import com.votacao.assembleia.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas/{pautaId}/votos")
@Tag(name = "Voto", description = "API para gerenciamento de votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @Operation(
            summary = "Registrar voto",
            description = "Registra o voto de um associado em uma pauta específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voto registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Voto inválido ou duplicado"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
            @ApiResponse(responseCode = "409", description = "Associado já votou nesta pauta")
    })
    @PostMapping
    public ResponseEntity<Voto> votar(
            @PathVariable Long pautaId,
            @RequestBody VotoRequestDTO request) {
        return ResponseEntity.ok(votoService.registrarVoto(pautaId, request));
    }
}
