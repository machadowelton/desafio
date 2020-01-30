package br.com.machadowelton;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.exceptions.ErroInternoException;
import br.com.machadowelton.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.services.ProdutoService;
import br.com.machadowelton.services.impl.ProdutoServiceImpl;
import br.com.machadowelton.services.repositories.products.ProdutoRepository;

@RunWith(SpringRunner.class)
public class ProdutoServiceImplTest {
	
	@Mock
	private ProdutoRepository produtoRepository;
	
	private ProdutoService produtoService;
	
	private Produto produto1;
	
	private final String PRODUTO_1_ID = "S11a21q3q5e6et4t1g";
	private final String PRODUTO_1_NAME = "ESCOLAR";
	
	
	@Before
	public void setUp() {
		this.produtoService = new ProdutoServiceImpl(produtoRepository);
		produto1 = Produto.builder()
								.id(PRODUTO_1_ID)
								.name(PRODUTO_1_NAME)
								.build();		
	}
	
	@Test
	public void test_listar_todos() {
		when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1));
		List<Produto> produtos = produtoService.listarTodos();
		verify(produtoRepository).findAll();
		assertThat(produtos.equals(Arrays.asList(produto1)));
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void test_listar_todos_produtos_nao_encontrado() {
		when(produtoRepository.findAll()).thenReturn(null);
		produtoService.listarTodos();
		verify(produtoRepository).findAll();
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_listar_todos_erro_interno() {
		when(produtoRepository.findAll()).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		produtoService.listarTodos();
		verify(produtoRepository).findAll();
	}
	
	@Test
	public void test_buscar_por_nome() {
		when(produtoRepository.findByName(PRODUTO_1_NAME)).thenReturn(Optional.of(produto1));
		Produto produto = produtoService.buscarPorNome(PRODUTO_1_NAME);
		verify(produtoRepository).findByName(PRODUTO_1_NAME);
		assertThat(produto.equals(produto1)).isEqualTo(Boolean.TRUE);
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void test_buscar_por_nome_nao_encontrado() {
		when(produtoRepository.findByName(PRODUTO_1_NAME)).thenReturn(Optional.ofNullable(null));
		produtoService.buscarPorNome(PRODUTO_1_NAME);
		verify(produtoRepository).findByName(PRODUTO_1_NAME);
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_buscar_por_nome_erro_interno() {
		when(produtoRepository.findByName(PRODUTO_1_NAME)).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		produtoService.buscarPorNome(PRODUTO_1_NAME);
		verify(produtoRepository).findByName(PRODUTO_1_NAME);
	}
	
}
