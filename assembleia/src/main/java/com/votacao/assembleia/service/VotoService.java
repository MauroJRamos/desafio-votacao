package com.votacao.assembleia.service;

import com.votacao.assembleia.dto.ResultadoVotacaoDTO;
import com.votacao.assembleia.dto.VotoRequestDTO;
import com.votacao.assembleia.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.votacao.assembleia.model.Pauta;
import com.votacao.assembleia.model.Voto;
import com.votacao.assembleia.repository.PautaRepository;
import com.votacao.assembleia.repository.VotoRepository;

import com.votacao.assembleia.enums.StatusSessao;
import com.votacao.assembleia.enums.TipoVoto;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VotoService {
    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;

    public VotoService(VotoRepository votoRepository, PautaRepository pautaRepository) {
        this.votoRepository = votoRepository;
        this.pautaRepository = pautaRepository;
    }

    @Transactional
    public Voto registrarVoto(Long pautaId, VotoRequestDTO request) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));

        if (pauta.getStatusSessao() != StatusSessao.EM_ANDAMENTO) {
            throw new ResourceNotFoundException("Sessão de votação não está aberta");
        }

        if (LocalDateTime.now().isAfter(pauta.getFimSessao())) {
            throw new ResourceNotFoundException("Sessão de votação encerrada");
        }

        if (votoRepository.existsByPautaIdAndAssociadoId(pautaId, request.getAssociadoId())) {
            throw new ResourceNotFoundException("Associado já votou nesta pauta");
        }

        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setAssociadoId(request.getAssociadoId());
        voto.setVoto(request.getVoto());
        voto.setDataHora(LocalDateTime.now());

        return votoRepository.save(voto);
    }

    @Transactional(readOnly = true)
    public ResultadoVotacaoDTO contabilizarVotos(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));

        List<Voto> votos = votoRepository.findByPautaId(pautaId);

        int votosSim = (int) votos.stream()
                .filter(v -> v.getVoto() == TipoVoto.SIM)
                .count();

        int votosNao = (int) votos.stream()
                .filter(v -> v.getVoto() == TipoVoto.NAO)
                .count();

        ResultadoVotacaoDTO resultado = new ResultadoVotacaoDTO();
        resultado.setPautaId(pautaId);
        resultado.setTitulo(pauta.getTitulo());
        resultado.setVotosSim(votosSim);
        resultado.setVotosNao(votosNao);
        resultado.setResultado(votosSim > votosNao ? "APROVADA" : "REJEITADA");

        return resultado;
    }
}
