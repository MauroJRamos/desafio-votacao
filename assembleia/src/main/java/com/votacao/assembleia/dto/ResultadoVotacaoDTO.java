package com.votacao.assembleia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Requisição apurar resultado a apuração")
@Data
public class ResultadoVotacaoDTO {

    @Schema(description = "ID da pauta", example = "01")
    private Long pautaId;
    @Schema(description = "Título da pauta", example = "Aprovação do orçamento 2024")
    private String titulo;
    @Schema(description = "Contagem de votos sim")
    private int votosSim;
    @Schema(description = "Contagem de votos não")
    private int votosNao;
    @Schema(description = "Resultado da votação", example = "APROVADO ou DESAPROVADO")
    private String resultado;

}
