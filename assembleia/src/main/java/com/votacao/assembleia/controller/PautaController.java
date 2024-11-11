package com.votacao.assembleia.controller;

import com.votacao.assembleia.dto.PautaRequestDTO;
import com.votacao.assembleia.dto.ResultadoVotacaoDTO;
import com.votacao.assembleia.model.Pauta;
import com.votacao.assembleia.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas")
@Tag(name = "Pauta", description = "API para gerenciamento de pautas")
public class PautaController {
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @Operation(
            summary = "Criar nova pauta",
            description = "Cria uma nova pauta para votação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody PautaRequestDTO request) {
        return ResponseEntity.ok(pautaService.criarPauta(request));
    }

    @Operation(
            summary = "Abrir sessão de votação",
            description = "Abre uma sessão de votação para uma pauta específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão aberta com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
            @ApiResponse(responseCode = "400", description = "Sessão já aberta ou outros erros")
    })
    @PostMapping("/{id}/sessao")
    public ResponseEntity<Pauta> abrirSessao(
            @PathVariable Long id,
            @RequestParam(required = false) Integer duracaoMinutos) {
        return ResponseEntity.ok(pautaService.abrirSessao(id, duracaoMinutos));
    }

    @Operation(
            summary = "Obter resultado da votação",
            description = "Retorna o resultado final da votação de uma pauta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado obtido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
    })
    @GetMapping("/{id}/resultado")
    public ResponseEntity<ResultadoVotacaoDTO> resultado(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.contabilizarVotos(id));
    }
}
