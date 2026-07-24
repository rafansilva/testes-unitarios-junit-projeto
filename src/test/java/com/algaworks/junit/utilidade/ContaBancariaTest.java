package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Test
    public void saldoNaoPodeSerNulo() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null));

        assertEquals("Saldo inválido.", illegalArgumentException.getMessage());
    }

    @Test
    public void saldoPodeSerNegativo() {
        BigDecimal saldoNegativo = new BigDecimal("-100");
        assertDoesNotThrow(() -> new ContaBancaria(saldoNegativo));
    }

    @Test
    public void saldoPodeSerZero() {
        assertDoesNotThrow(() -> new ContaBancaria(BigDecimal.ZERO));
    }

    @Test
    public void saqueValorNaoPodeSerNulo() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(1000));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null));
        assertEquals("Valor inválido.", illegalArgumentException.getMessage());
    }

    @Test
    public void saqueValorNaoPodeSerZeroOuNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(1000));

        IllegalArgumentException valorNegativo = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(new BigDecimal("-100")));
        IllegalArgumentException valorZero = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO));


        assertAll("Validado valor de entrada",
                () -> assertEquals("Valor não pode ser zero.", valorZero.getMessage()),
                () -> assertEquals("Valor não pode ser negativo.", valorNegativo.getMessage())
        );
    }

    @Test
    public void saqueSaldoInsuficiente() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(1000));
        BigDecimal valorDeSaque = new BigDecimal("2000");
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorDeSaque));
        assertEquals("Saldo insuficiente.", runtimeException.getMessage());
    }

    @Test
    public void saqueNormal() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(1000));
        BigDecimal valorDeSaque = new BigDecimal("500");

        contaBancaria.saque(valorDeSaque);

        BigDecimal saldoFinalDaConta = contaBancaria.saldo();

        assertEquals(valorDeSaque, saldoFinalDaConta);
    }

    @Test
    public void



}