package br.com.machadowelton.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.domain.dto.ProdutoDTO;
import br.com.machadowelton.domain.exceptions.ErroInternoException;
import br.com.machadowelton.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.domain.exceptions.ValidacaoException;
import br.com.machadowelton.services.UsuarioService;
import br.com.machadowelton.services.repositories.common.ProdutoUsuarioRepository;
import br.com.machadowelton.services.repositories.products.ProdutoRepository;
import br.com.machadowelton.services.repositories.users.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.repository = usuarioRepository;
	}
	
	@Autowired
	private ProdutoUsuarioRepository produtoUsuarioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Override
	public Usuario buscarPorId(String id) {
		try {
			return repository
						.findById(id)
						.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum usuário encontrado pelo id: " + id));
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch (Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public List<Usuario> listarTodos() {
		try {
			return Optional
						.ofNullable(repository.findAll())
						.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum usuário encontrado"));
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch (Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public Usuario inserir(Usuario usuario) {
		try {
			if(repository.existsByCpf(usuario.getCpf()))
				throw new ValidacaoException("Já existe um usuário com o cpf: " + usuario.getCpf());
			return repository.save(usuario);
		} catch (ValidacaoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public Usuario atualizar(String id, Usuario usuario) {
		try {
			if(!repository.existsById(id))
				throw new RecursoNaoEncontradoException("Nenhum usuário encontrado pelo id: " + id);
			Usuario usuarioReturn = repository.findById(id).get();
			if(!usuario.getCpf().equals(usuarioReturn.getCpf()) && repository.existsByCpf(usuario.getCpf()))
				throw new ValidacaoException("Já existe um usuário com o cpf: " + usuario.getCpf());
			return repository.save(usuario);
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch(ValidacaoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public void removerPorId(String id) {
		try {
			if(!repository.existsById(id))
				throw new RecursoNaoEncontradoException("Nenhum usuário encontrado pelo id: " + id);
			repository.deleteById(id);
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public List<ProdutoDTO> listarProtudos(String id) {
		try {
			return produtoUsuarioRepository.findByIdUsuario(id)
					.stream()
					.map( m -> {
						Produto produto = produtoRepository.findById(m.getIdProduto())
													.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum produto encontrado para o id: " + m.getIdProduto()));
						return new ProdutoDTO(produto.getId(), produto.getName(), m.getStatus(), m.getId());
					})
					.collect(Collectors.toList());
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

}
