package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SaudacaoUtilTest {

    @Test
    public void Dado_uma_horario_a_matuino_Quando_saudar_Entao_deve_retornar_bom_dia() {
        //Arrange - prepara o cenário
        int hora = 9;

        //Act - executa o cenário
        String saudacao = SaudacaoUtil.saudar(hora);

        //Assert - realiza a asserção
        assertEquals("Bom dia", saudacao);
    }

    @Test
    public void Dado_uma_horario_vespertino_Quando_saudar_Entao_deve_retornar_boa_tarde() {
        int hora = 15;
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals("Boa tarde", saudacao);
    }

    @Test
    public void Dado_uma_horario_noturno_Quando_saudar_Entao_deve_retornar_boa_noite() {
        int horaValida = 4;
        String saudacao = SaudacaoUtil.saudar(horaValida);
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }


    @Test
    public void Dado_uma_horario_invalida_Quando_saudar_Entao_deve_lancar_exception() {
        int horaInvalida = -10;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(horaInvalida));
        assertEquals("Hora inválida", exception.getMessage());
    }

    //pode não ser util nesse contexto, mas existe essa possibilidade
    @Test
    public void Dado_uma_horario_valida_Quando_saudar_Entao_nao_deve_lancar_exception() {
        int horaValida = 0;
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(horaValida));
    }

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9,10,11})
    public void Dado_horario_matinal_Quando_saudar_Entao_deve_retornar_bom_dia(int hora) {
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals("Bom dia", saudacao);
    }
}