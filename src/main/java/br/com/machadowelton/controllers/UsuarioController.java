package br.com.machadowelton.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.domain.dto.ProdutoDTO;
import br.com.machadowelton.services.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl service;
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Usuario buscarPorId(@PathVariable("id") String id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Usuario> listarTodos() {
		return service.listarTodos();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Usuario inserir(@RequestBody @Valid Usuario usuario) {
		return service.inserir(usuario);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Usuario atualizar(@PathVariable("id") String id, @RequestBody @Valid Usuario usuario) {
		return service.atualizar(id, usuario);
	}
	
	@DeleteMapping(value = "/{id}")
	public void removerPorId(@PathVariable("id") String id) {
		service.removerPorId(id);
	}

	@GetMapping(value = "/{id}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProdutoDTO> buscarProdutosPorIdUsuario(@PathVariable("id") String id) {
		return service.listarProtudos(id);
	}
	
}
