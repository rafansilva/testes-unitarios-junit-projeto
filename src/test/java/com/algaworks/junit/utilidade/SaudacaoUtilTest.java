package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudarComBomDia() {
        //Arrange - prepara o cenário
        int hora = 9;

        //Act - executa o cenário
        String saudacao = SaudacaoUtil.saudar(hora);

        //Assert - realiza a asserção
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
        int horaInvalida = -10;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(horaInvalida));
        assertEquals("Hora inválida", exception.getMessage());
    }

    //pode não ser util nesse contexto, mas existe essa possibilidade
    @Test
    public void naoDeveLancarException() {
        int horaValida = 0;
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(horaValida));
    }
}