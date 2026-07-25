package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compra")
class CarrinhoCompraTest {

    private Cliente cliente;
    private final List<ItemCarrinhoCompra> itens = new ArrayList<>();
    private CarrinhoCompra carrinhoCompra;
    private Produto notebook;
    private Produto cadeira;

    @Nested
    @DisplayName("Dado um carrinho de compra com 2 produtos")
    class CarrinhoDeCompraCom2Produtos {

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "Rafael");

            notebook = new Produto(1L, "Notebook Pro Gamer", "Notebook Alienware 15", new BigDecimal("7000.00"));
            cadeira = new Produto(1L, "Cadeira Gamer", "Cadeira Ergonomica Gamer", new BigDecimal("4000.00"));

            itens.add(new ItemCarrinhoCompra(notebook, 1));
            itens.add(new ItemCarrinhoCompra(cadeira, 1));

            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Nested
        @DisplayName("Quando solicitar a lista de compras")
        class RetornaUmaLista {

            @Test
            @DisplayName("Então retorna a lista de itens do carrinho")
            void retornaUmaNovaLista() {
                assertIterableEquals(new ArrayList<>(itens), carrinhoCompra.getItens());
            }
        }

        @Nested
        @DisplayName("Quando adicionar um produto nulo")
        class AdicionarProdutoNulo {

            @Test
            @DisplayName("Então deve lançar uma exception")
            void adicionarProdutoInvalido() {
                assertThrows(NullPointerException.class, () -> carrinhoCompra.adicionarProduto(null, 2));
            }
        }

        @Nested
        @DisplayName("Quando adicionar uma quantidade inválida")
        class AdicionarQuantidadeInvalida {

            @Test
            @DisplayName("Então deve lançar uma exception")
            void adicionarQuantidadeInvalida() {
                assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.adicionarProduto(notebook, 0));
            }
        }

        @Nested
        @DisplayName("Quando adicionar mais um produto ao carrinho")
        class AdicionarProduto {

            @Test
            @DisplayName("Então deve adicionar mais um item na lista de compra do carrinho")
            void AdicionarMaisProdutoNoCarrinho() {
                Produto produto = new Produto(1L, "Meia", "Meia grossa para o frio", new BigDecimal("20.00"));
                carrinhoCompra.adicionarProduto(produto, 2);

                assertEquals(3, carrinhoCompra.getItens().size());
            }
        }


    }
}