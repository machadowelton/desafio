package br.com.machadowelton.services;

import java.util.List;

import br.com.machadowelton.domain.Produto;

public interface ProdutoService {
	
	List<Produto> listarTodos();
	
	Produto buscarPorNome(String nome);
	
}
