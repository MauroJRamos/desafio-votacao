package com.votacao.assembleia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.assembleia.dto.VotoRequestDTO;
import com.votacao.assembleia.enums.TipoVoto;
import com.votacao.assembleia.model.Pauta;
import com.votacao.assembleia.model.Voto;
import com.votacao.assembleia.service.VotoService;
import com.votacao.assembleia.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VotoService votoService;

    private VotoRequestDTO votoRequest;
    private Voto votoResponse;
    private final Long PAUTA_ID = 1L;
    private final Long ASSOCIADO_ID = 1L;

    @BeforeEach
    void setUp() {
        // Configurar o objeto de requisição
        votoRequest = new VotoRequestDTO();
        votoRequest.setAssociadoId(String.valueOf(ASSOCIADO_ID));
        votoRequest.setVoto(TipoVoto.SIM);

        // Configurar o objeto de resposta
        Pauta pauta = new Pauta();
        pauta.setId(PAUTA_ID);
        pauta.setTitulo("Pauta Teste");

        votoResponse = new Voto();
        votoResponse.setId(1L);
        votoResponse.setPauta(pauta);
        votoResponse.setAssociadoId(String.valueOf(ASSOCIADO_ID));
        votoResponse.setVoto(TipoVoto.SIM);
        votoResponse.setDataHora(LocalDateTime.now());
    }

    @Test
    void deveRegistrarVotoComSucesso() throws Exception {
        when(votoService.registrarVoto(eq(PAUTA_ID), any(VotoRequestDTO.class)))
                .thenReturn(votoResponse);

        mockMvc.perform(post("/api/v1/pautas/{pautaId}/votos", PAUTA_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(votoResponse.getId()))
                .andExpect(jsonPath("$.associadoId").value(ASSOCIADO_ID))
                .andExpect(jsonPath("$.voto").value(TipoVoto.SIM.toString()));
    }

    @Test
    void deveRetornar404QuandoPautaNaoEncontrada() throws Exception {
        when(votoService.registrarVoto(eq(PAUTA_ID), any(VotoRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("Pauta não encontrada"));

        mockMvc.perform(post("/api/v1/pautas/{pautaId}/votos", PAUTA_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pauta não encontrada"));
    }

    @Test
    void deveRetornar400QuandoSessaoEncerrada() throws Exception {
        when(votoService.registrarVoto(eq(PAUTA_ID), any(VotoRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("Sessão de votação encerrada"));

        mockMvc.perform(post("/api/v1/pautas/{pautaId}/votos", PAUTA_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Sessão de votação encerrada"));
    }

}
