package br.com.machadowelton.services;

import java.util.List;

import br.com.machadowelton.domain.Usuario;

public interface ProdutoUsuarioService {
	
	void associarProdutoUsuario(String nomeProduto, String idUsuario);
	
	List<Usuario> listarUsoProduto(String idPruduto);
	
	List<Usuario> listarUsoProduto();
	
}
