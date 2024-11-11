package com.votacao.assembleia.service;

import com.votacao.assembleia.dto.PautaRequestDTO;
import com.votacao.assembleia.dto.ResultadoVotacaoDTO;
import com.votacao.assembleia.enums.StatusSessao;
import com.votacao.assembleia.model.Pauta;

import com.votacao.assembleia.repository.PautaRepository;
import com.votacao.assembleia.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;
    private final VotoService votoService;

    public PautaService(PautaRepository pautaRepository, VotoService votoService) {
        this.pautaRepository = pautaRepository;
        this.votoService = votoService;
    }

    @Transactional
    public Pauta criarPauta(PautaRequestDTO request) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(request.getTitulo());
        pauta.setDescricao(request.getDescricao());
        return pautaRepository.save(pauta);
    }

    @Transactional
    public Pauta abrirSessao(Long pautaId, Integer duracaoMinutos) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));

        if (pauta.getStatusSessao() != StatusSessao.NAO_INICIADA) {
            throw new ResourceNotFoundException("Sessão já foi iniciada ou está encerrada");
        }

        int duracao = duracaoMinutos != null ? duracaoMinutos : 1;
        LocalDateTime agora = LocalDateTime.now();

        pauta.setInicioSessao(agora);
        pauta.setFimSessao(agora.plusMinutes(duracao));
        pauta.setStatusSessao(StatusSessao.EM_ANDAMENTO);

        return pautaRepository.save(pauta);
    }

    @Transactional(readOnly = true)
    public ResultadoVotacaoDTO contabilizarVotos(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));

        if (pauta.getStatusSessao() != StatusSessao.ENCERRADA &&
                LocalDateTime.now().isAfter(pauta.getFimSessao())) {
            atualizarStatusSessao(pauta);
        }

        return votoService.contabilizarVotos(pautaId);
    }

    private void atualizarStatusSessao(Pauta pauta) {
        if (LocalDateTime.now().isAfter(pauta.getFimSessao())) {
            pauta.setStatusSessao(StatusSessao.ENCERRADA);
            pautaRepository.save(pauta);
        }
    }
}
