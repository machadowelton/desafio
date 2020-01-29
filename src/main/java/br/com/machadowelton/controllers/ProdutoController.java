package br.com.machadowelton.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.services.impl.ProdutoServiceImpl;
import br.com.machadowelton.services.impl.ProdutoUsuarioServiceImpl;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoServiceImpl service;
	
	@Autowired
	private ProdutoUsuarioServiceImpl produtoUsuarioServiceImpl;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Produto> listarTodos() {
		return service.listarTodos();
	}
	
	@GetMapping(value = "/usuarios")
	public List<Usuario> listarUsuariosProduto(@RequestParam("id_produto") String id_produto) {
		Optional<String> opIdProduto = Optional.ofNullable(id_produto);
		if(opIdProduto.isPresent())
			return produtoUsuarioServiceImpl.listarUsoProduto(opIdProduto.get());
		return produtoUsuarioServiceImpl.listarUsoProduto();
	}
	
	@PutMapping(value = "/associar/usuario")
	public void associarProdutoUsuario(
			@RequestParam("nome_produto") String nomeProduto, 
			@RequestParam("id_usuario") String idUsuario) {
		produtoUsuarioServiceImpl.associarProdutoUsuario(nomeProduto, idUsuario);
	}
	
}
