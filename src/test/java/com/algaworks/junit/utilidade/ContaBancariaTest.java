package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Test
    void saldoNaoPodeSerNulo() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null));

        assertEquals("Valor inválido.", illegalArgumentException.getMessage());
    }

    @Test
    void saldoPodeSerNegativo() {
        BigDecimal saldoNegativo = new BigDecimal("-100");
        assertDoesNotThrow(() -> new ContaBancaria(saldoNegativo));
    }

    @Test
    void saldoPodeSerZero() {
        assertDoesNotThrow(() -> new ContaBancaria(BigDecimal.ZERO));
    }

    @Test
    void saldo() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal("29.90"));
        assertEquals(new BigDecimal("29.90"), contaBancaria.saldo());
    }

    @Test
    void saqueValorNaoPodeSerNulo() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(1000));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null));
        assertEquals("Valor inválido.", illegalArgumentException.getMessage());
    }

    @Test
    void saqueValorNaoPodeSerZero() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO));
    }

    @Test
    void saqueValorNaoPodeSerNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(new BigDecimal("-100")));
    }


    @Test
    void saqueSaldoInsuficiente() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(1000));
        BigDecimal valorDeSaque = new BigDecimal("2000");
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorDeSaque));
        assertEquals("Saldo insuficiente.", runtimeException.getMessage());
    }

    @Test
    public void saqueValor() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal("1000"));
        contaBancaria.saque(new BigDecimal("500"));

        BigDecimal saldoFinalDaConta = contaBancaria.saldo();

        assertEquals(new BigDecimal("500"), saldoFinalDaConta);
    }

    @Test
    void depositoValorNaoPodeSerNulo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(null));
        assertEquals("Valor inválido.", illegalArgumentException.getMessage());
    }

    @Test
    void depositoValorNaoPodeSerZero() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(BigDecimal.ZERO));
        assertEquals("Valor inválido.", illegalArgumentException.getMessage());
    }

    @Test
    void depositoValorNaoPodeSerNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(new BigDecimal("-100")));
        assertEquals("Valor inválido.", illegalArgumentException.getMessage());
    }

    @Test
    void depositarValor() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);

        contaBancaria.deposito(BigDecimal.TEN);

        BigDecimal saldoFinalDaConta = contaBancaria.saldo();

        assertEquals(new BigDecimal("20"), saldoFinalDaConta);
    }

    @Test
    void saqueAposDeposito() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal("100"));
        contaBancaria.deposito(new BigDecimal("50"));
        contaBancaria.saque(BigDecimal.TEN);
        assertEquals(new BigDecimal("140"), contaBancaria.saldo());
    }


}