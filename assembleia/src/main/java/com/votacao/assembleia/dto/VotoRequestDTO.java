package com.votacao.assembleia.dto;

import com.votacao.assembleia.enums.TipoVoto;
import lombok.Data;

@Data
public class VotoRequestDTO {
    private String associadoId;
    private TipoVoto voto;
}
