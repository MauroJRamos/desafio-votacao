package com.votacao.assembleia.dto;

import lombok.Data;

@Data
public class ResultadoVotacaoDTO {

    private Long pautaId;
    private String titulo;
    private int votosSim;
    private int votosNao;
    private String resultado;

}
