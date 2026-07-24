package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudarComBomDia() {
        int hora = 9;
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals("Bom dia", saudacao);
    }

    @Test
    public void saudarComBoaTarde() {
        int hora = 15;
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals("Boa tarde", saudacao);
    }

    @Test
    public void saudarComBoaNoite() {
        int hora = 21;
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals("Boa noite", saudacao);
    }

    @Test
    public void saudarComBomDiaAPartir5h() {
        String saudacao = SaudacaoUtil.saudar(5);

        assertEquals("Bom dia", saudacao, "Saudação incorreta!");

    }

    @Test
    public void saudarComBoaNoiteAte4h() {
        String saudacao = SaudacaoUtil.saudar(4);

        assertEquals("Boa noite", saudacao, "Saudação incorreta!");

    }


    @Test
    public void deveLancarException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(-10));

        assertEquals("Hora inválida", illegalArgumentException.getMessage());
    }

    //pode não ser util nesse contexto, mas existe essa possibilidade
    @Test
    public void naoDeveLancarException() {
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));
    }
}