package com.votacao.assembleia.repository;

import com.votacao.assembleia.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
