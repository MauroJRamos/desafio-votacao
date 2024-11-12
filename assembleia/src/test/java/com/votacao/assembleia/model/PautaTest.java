package com.votacao.assembleia.model;

import com.votacao.assembleia.enums.StatusSessao;
import com.votacao.assembleia.service.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.time.LocalDateTime;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PautaTest {

    @Mock
    private PautaService pautaService;


    private Pauta pauta;

    @BeforeEach
    void setUp() {
        pauta = new Pauta();
    }

    @Test
    void shouldCreatePauta() {
        pauta.setTitulo("Exemplo de Pauta");
        pauta.setDescricao("Descrição da pauta de exemplo");

        assertEquals("Exemplo de Pauta", pauta.getTitulo());
        assertEquals("Descrição da pauta de exemplo", pauta.getDescricao());
        assertEquals(StatusSessao.NAO_INICIADA, pauta.getStatusSessao());
        assertNull(pauta.getInicioSessao());
        assertNull(pauta.getFimSessao());
    }

    @Test
    void shouldOpenSession() {
            // Arrange
            pauta.setId(1L);
            pauta.setTitulo("Pauta de Teste");
            LocalDateTime inicio = LocalDateTime.now();
            LocalDateTime fim = inicio.plusMinutes(10);

            //Configure o mock para retornar uma nova pauta com a sessão aberta
            Pauta pautaAberta = new Pauta();
            pautaAberta.setId(1L);
            pautaAberta.setTitulo("Pauta de Teste");
            pautaAberta.setStatusSessao(StatusSessao.EM_ANDAMENTO);
            pautaAberta.setInicioSessao(inicio);
            pautaAberta.setFimSessao(fim);

            when(pautaService.abrirSessao(1L, 10)).thenReturn(pautaAberta);

            // Act
            Pauta resultado = pautaService.abrirSessao(1L, 10);

            // Assert
            verify(pautaService).abrirSessao(1L, 10);
            assertEquals(StatusSessao.EM_ANDAMENTO, resultado.getStatusSessao());
            assertNotNull(resultado.getInicioSessao());
            assertNotNull(resultado.getFimSessao());
            assertTrue(resultado.getFimSessao().isAfter(resultado.getInicioSessao()));
            assertEquals(10, resultado.getFimSessao().getMinute() - resultado.getInicioSessao().getMinute());
        }




}
