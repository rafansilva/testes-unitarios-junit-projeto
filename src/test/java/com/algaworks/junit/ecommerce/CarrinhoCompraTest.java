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
    private List<ItemCarrinhoCompra> itens = new ArrayList<>();
    private CarrinhoCompra carrinhoCompra;
    private Produto notebook;
    private Produto cadeira;
    private Produto tablet;

    @Nested
    @DisplayName("Dado um carrinho de compra com 2 itens")
    class CarrinhoDeCompraCom2Itens {

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "Rafael");

            notebook = new Produto(1L, "Notebook Pro Gamer", "Notebook Alienware 15", new BigDecimal("7000"));
            cadeira = new Produto(1L, "Cadeira Gamer", "Cadeira Ergonomica Gamer", new BigDecimal("4000"));

            itens.add(new ItemCarrinhoCompra(notebook, 1));
            itens.add(new ItemCarrinhoCompra(cadeira, 1));

            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Nested
        @DisplayName("Quando retorna itens")
        class QuandoRetornaItens {

            @Test
            @DisplayName("Então deve retornar dois itens")
            void entaoDeveRetornarDoisItens() {
                assertEquals(2, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E deve retorna uma nova lista de itens")
            void retornaUmaNovaLista() {
                carrinhoCompra.getItens().clear();
                assertEquals(2, carrinhoCompra.getItens().size());
            }
        }

        @Nested
        @DisplayName("Quando remover um item")
        class QuandoRemoverUmItem {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.removerProduto(notebook);
            }

            @Test
            @DisplayName("Então deve diminuir a quantidade total de itens")
            void entaoDeveDiminuirQuantidadeTotal() {
                assertEquals(1, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E não remover demais itens")
            void NaoDeveRemoverDemaisItens() {
                assertEquals(cadeira, carrinhoCompra.getItens().get(0).getProduto());
            }
        }

        @Nested
        @DisplayName("Quando aumentar quantidade de um item")
        class QuandoAumentarQuantidade {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.aumentarQuantidadeProduto(notebook);
            }

            @Test
            @DisplayName("Então deve somar quantidade dos itens iguais")
            void deveSomarNaQuantidade() {
                assertEquals(2, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("Então deve retornar dois de quantidade total de itens")
            void deveRetornarQuantidadeTotalItens() {
                assertEquals(2, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("Então deve aumentar a quantidade caso seja o mesmo produto")
            void adicionarQuantidadeParaMesmoProduto() {
                carrinhoCompra.adicionarProduto(notebook, 1);
                assertEquals(3, carrinhoCompra.getQuantidadeDoProduto(notebook));
            }

            @Test
            @DisplayName("Então deve retornar valor total correto de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(new BigDecimal("18000"), carrinhoCompra.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando diminuir quantidade de um item com apenas um de quantidade")
        class QuandoDiminuirQuantidadeDeItemUnico {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.diminuirQuantidadeProduto(notebook);
            }

            @Test
            @DisplayName("Então deve remover item")
            void entaoDeveRemoverItem() {
                assertNotEquals(carrinhoCompra.getItens().get(0).getProduto(), notebook);
            }
        }

        @Nested
        @DisplayName("Quando adicionar item com quantidade inválida")
        class QuandoAdicionarItemComQuantidadeInvalida {

            @Test
            @DisplayName("Então deve lançar exception")
            void entaoDeveFalhar() {
                assertThrows(RuntimeException.class, () -> carrinhoCompra.adicionarProduto(notebook, -1));
            }
        }

        @Nested
        @DisplayName("Quando esvaziar carrinho")
        class QuandoEsvaziarCarrinho {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.esvaziar();
            }

            @Test
            @DisplayName("Então deve somar a quantidade total de itens")
            void deveSomarQuantidade() {
                assertEquals(0, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("Então deve retornar zero de quantidade total de itens")
            void deveRetrnarQuantidadeTotalItens() {
                assertEquals(0, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve retornar zero o valor total de itens")
            void deveRetornarValorTotalItens() {
                assertEquals(BigDecimal.ZERO, carrinhoCompra.getValorTotal());
            }
        }
    }

    @Nested
    @DisplayName("Dado um carrinho vazio")
    class DadoCarrinhoVazio {

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "Rafael");

            notebook = new Produto(1L, "Notebook Pro Gamer", "Notebook Alienware 15", new BigDecimal("7000"));
            cadeira = new Produto(1L, "Cadeira Gamer", "Cadeira Ergonomica Gamer", new BigDecimal("4000"));
            tablet = new Produto(3L, "Tablet", "Tablet", new BigDecimal("3000"));

            itens = new ArrayList<>();

            carrinhoCompra = new CarrinhoCompra(cliente, itens);
        }

        @Nested
        @DisplayName("Quando adicionar dois notebooks iguais e um tablet")
        class QuandoAdicionarDoisItensIguais {

            @BeforeEach
            void beforeEach() {
                carrinhoCompra.adicionarProduto(notebook, 1);
                carrinhoCompra.adicionarProduto(notebook, 1);
                carrinhoCompra.adicionarProduto(tablet, 1);
            }

            @Test
            @DisplayName("Então deve somar na quantidade dos itens iguais")
            void entaoDeveSomarNaQuantidade() {
                assertEquals(2, carrinhoCompra.getItens().get(0).getQuantidade());
                assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
            }

            @Test
            @DisplayName("E retornar três de quantidade total de itens")
            void eRetornarQuatidadeTotalItens() {
                assertEquals(3, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("E retornar valor total correto de itens")
            void eRetornarValorTotalItens() {
                assertEquals(new BigDecimal("17000"), carrinhoCompra.getValorTotal());
            }
        }

    }
}