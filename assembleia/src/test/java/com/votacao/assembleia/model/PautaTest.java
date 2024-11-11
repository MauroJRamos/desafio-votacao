package com.votacao.assembleia.model;

import com.votacao.assembleia.enums.StatusSessao;
import com.votacao.assembleia.service.PautaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PautaTest {

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private Pauta pauta;

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
        when(pautaService.abrirSessao(1L, 10)).thenReturn(pauta);

        pautaService.abrirSessao(1L, 10);

        assertEquals(StatusSessao.NAO_INICIADA, pauta.getStatusSessao());
        assertNull(pauta.getInicioSessao());
        assertNull(pauta.getFimSessao());

    }

}
