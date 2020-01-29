package br.com.machadowelton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.domain.exceptions.ErroInternoException;
import br.com.machadowelton.domain.exceptions.ValidacaoException;
import br.com.machadowelton.services.UsuarioService;
import br.com.machadowelton.services.impl.UsuarioServiceImpl;
import br.com.machadowelton.services.repositories.users.UsuarioRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsuarioServiceImplTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	
	@Before
	public void setUp() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
		usuario = Usuario.builder()
								.nomeCompleto("Welton")
								.cpf("40091217857")
								.telefone("11979697822")
								.endereco("Rua dos Tupiniquis")
								.email("email@email.com")
								.build();		
	}
	
	@Test
	public void inserirTest() {
		usuarioService.inserir(usuario);
		verify(usuarioRepository).save(usuario);
	}
	
	@Test(expected = ValidacaoException.class)
	public void inserirValidacaoExceptionExisteCpfCadastradoTest() {
		when(usuarioRepository.existsByCpf(usuario.getCpf())).thenReturn(Boolean.TRUE);
		usuarioService.inserir(usuario);
	}
	
	@Test(expected = ErroInternoException.class)
	public void inserirErroInternoExceptionTest() {
		when(usuarioRepository.save(usuario)).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		usuarioService.inserir(usuario);
	}
}
