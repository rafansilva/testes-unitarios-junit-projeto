package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		Objects.requireNonNull(cliente);
		Objects.requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		//TODO deve retornar uma nova lista para que a antiga não seja alterada
		return new ArrayList<>(itens);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {
		//TODO parâmetros não podem ser nulos, deve retornar uma exception
		//TODO quantidade não pode ser menor que 1
		//TODO deve incrementar a quantidade caso o produto já exista
		Objects.requireNonNull(produto);
		validaQuantidade(quantidade);

		encontrarItemPeloProduto(produto)
				.ifPresentOrElse(
						item -> item.adicionarQuantidade(quantidade),
						() -> adicionaNovoItem(produto, quantidade)
				);
	}

	public void removerProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		//TODO caso o produto não exista, deve retornar uma exception
		//TODO deve remover o produto independente da quantidade

		Objects.requireNonNull(produto);

		for (ItemCarrinhoCompra item : itens) {
			if (produto.equals(item.getProduto())) {
				itens.remove(item);
			} else {
				throw new IllegalArgumentException("Produto não existe no carrinho");
			}
		}
	}

	public void aumentarQuantidadeProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		//TODO caso o produto não exista, deve retornar uma exception
		//TODO deve aumentar em um quantidade do produto
	}

    public void diminuirQuantidadeProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		//TODO caso o produto não exista, deve retornar uma exception
		//TODO deve diminuir em um quantidade do produto, caso tenha apenas um produto, deve remover da lista
	}

    public BigDecimal getValorTotal() {
		//TODO implementar soma do valor total de todos itens
		return null;
    }

	public int getQuantidadeTotalDeProdutos() {
		//TODO retorna quantidade total de itens no carrinho
		//TODO Exemplo em um carrinho com 2 itens, com a quantidade 2 e 3 para cada item respectivamente, deve retornar 5
		return 0;
	}

	public void esvaziar() {
		//TODO deve remover todos os itens
	}

	public Optional<ItemCarrinhoCompra> encontrarItemPeloProduto(Produto produto) {
		return itens.stream()
				.filter(item -> item.getProduto().equals(produto))
				.findFirst();
	}

	public void adicionaNovoItem(Produto produto, int quatidade) {
		itens.add(new ItemCarrinhoCompra(produto, quatidade));
	}

	public void validaQuantidade(int quantidade) {
		if (quantidade <= 0) {
			throw new IllegalArgumentException("Quantidade inválida.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}