package br.com.machadowelton.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.exceptions.ErroInternoException;
import br.com.machadowelton.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.services.ProdutoService;
import br.com.machadowelton.services.repositories.products.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	
	@Autowired
	private ProdutoRepository repository;
	
	@Override
	public List<Produto> listarTodos() {
		try {
			return Optional.ofNullable(repository.findAll())
					.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum produto encontrado"));
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

	@Override
	public Produto buscarPorNome(String nome) {
		try {
			return repository.findByName(nome)
					.orElseThrow(() -> new RecursoNaoEncontradoException("Nenhuma produto encontrado pelo nome: " + nome));
		} catch (RecursoNaoEncontradoException e) {
			throw e;
		} catch(Exception e) {
			throw new ErroInternoException("Ocorreu um erro ao processar a requisição");
		}
	}

}
