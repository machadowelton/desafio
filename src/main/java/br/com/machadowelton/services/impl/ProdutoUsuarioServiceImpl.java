package br.com.machadowelton.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.ProdutoUsuario;
import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.domain.enums.EStatus;
import br.com.machadowelton.domain.exceptions.ErroInternoException;
import br.com.machadowelton.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.domain.exceptions.ValidacaoException;
import br.com.machadowelton.services.ProdutoUsuarioService;
import br.com.machadowelton.services.repositories.common.ProdutoUsuarioRepository;

@Service
public class ProdutoUsuarioServiceImpl implements ProdutoUsuarioService {

	@Autowired
	private ProdutoUsuarioRepository repository;
	
	@Autowired
	private ProdutoServiceImpl produtoService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Override
	public void associarProdutoUsuario(String nomeProduto, String idUsuario) {
		try {
			Produto produto = produtoService.buscarPorNome(nomeProduto);
			Usuario usuario = usuarioService.buscarPorId(idUsuario);
			Optional<ProdutoUsuario> opProdutoUsuarioExistente = repository.findByIdUsuarioAndStatus(usuario.getId() , EStatus.EM_USO);
			if(opProdutoUsuarioExistente.isPresent()) {				
				ProdutoUsuario produtoUsuarioAtualizar = opProdutoUsuarioExistente.get();
				if(produto.getId().equals(produtoUsuarioAtualizar.getIdProduto()))
					throw new ValidacaoException("Já existe uma relação entre o usuário: "+ usuario.getId() + " e produto: " + nomeProduto);
				produtoUsuarioAtualizar.setStatus(EStatus.USADO);
				repository.save(produtoUsuarioAtualizar);
			}
			ProdutoUsuario produtoUsuarioNovo = new ProdutoUsuario(produto.getId(), usuario.getId());
			repository.save(produtoUsuarioNovo);
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch(ValidacaoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu ume erro ao processar a requisição");
		}
	}

	@Override
	public List<Usuario> listarUsoProduto(String idPruduto) {
		try {
			return repository.findByStatusAndIdProduto(EStatus.EM_USO, idPruduto)
					.stream()
					.map(m -> {								
						return usuarioService.buscarPorId(m.getIdUsuario());								 
					})
					.collect(Collectors.toList());
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch (Exception e) {
			throw new ErroInternoException("Ocorreu ume erro ao processar a requisição");
		}
	}

	@Override
	public List<Usuario> listarUsoProduto() {
		try {
			return repository.findByStatus(EStatus.EM_USO)
					.stream()
					.map(m -> {								
						return usuarioService.buscarPorId(m.getIdUsuario());								 
					})
					.collect(Collectors.toList());
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch (Exception e) {
			throw new ErroInternoException("Ocorreu ume erro ao processar a requisição");
		}
	}

}
