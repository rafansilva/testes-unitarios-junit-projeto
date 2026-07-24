package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudar() {
        String bomDia = SaudacaoUtil.saudar(9);
        String boaTarde = SaudacaoUtil.saudar(14);
        String boaNoite = SaudacaoUtil.saudar(21);

        assertAll("Validando Saudações",
                () -> assertEquals("Bom dia", bomDia, "Saudação incorreta!"),
                () -> assertEquals("Boa tarde", boaTarde, "Saudação incorreta!"),
                () -> assertEquals("Boa noite", boaNoite, "Saudação incorreta!"));

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