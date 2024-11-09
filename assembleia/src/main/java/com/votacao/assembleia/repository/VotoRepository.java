package com.votacao.assembleia.repository;

import com.votacao.assembleia.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByPautaIdAndAssociadoId(Long pautaId, String associadoId);
    List<Voto> findByPautaId(Long pautaId);
}
