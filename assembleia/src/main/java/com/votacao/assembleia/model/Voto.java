package com.votacao.assembleia.model;


import com.votacao.assembleia.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pauta_id", "associado_id"})
})
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    private String associadoId;

    @Enumerated(EnumType.STRING)
    private TipoVoto voto;

    private LocalDateTime dataHora;
}
