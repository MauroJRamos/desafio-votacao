package com.votacao.assembleia.model;
import com.votacao.assembleia.enums.StatusSessao;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusSessao statusSessao = StatusSessao.NAO_INICIADA;

    private LocalDateTime inicioSessao;
    private LocalDateTime fimSessao;
}

