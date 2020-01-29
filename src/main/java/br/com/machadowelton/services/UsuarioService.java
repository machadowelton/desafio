package br.com.machadowelton.services;

import java.util.List;

import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.domain.dto.ProdutoDTO;

public interface UsuarioService {
	
	Usuario buscarPorId(String id);
	
	List<Usuario> listarTodos();
	
	Usuario inserir(Usuario usuario);
	
	Usuario atualizar(String id, Usuario usuario);
	
	void removerPorId(String id);
	
	List<ProdutoDTO> listarProtudos(String id);
	
}
