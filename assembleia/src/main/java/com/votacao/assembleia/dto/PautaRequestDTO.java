package com.votacao.assembleia.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Requisição para criação de pauta")
@Data
public class PautaRequestDTO {
    @Schema(description = "Título da pauta", example = "Aprovação do orçamento 2024")
    private String titulo;
    @Schema(description = "Descrição detalhada da pauta", example = "Votação para aprovação do orçamento do próximo ano")
    private String descricao;
}
