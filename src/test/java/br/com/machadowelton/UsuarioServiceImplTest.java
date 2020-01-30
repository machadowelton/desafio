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
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.machadowelton.domain.Usuario;
import br.com.machadowelton.domain.exceptions.ErroInternoException;
import br.com.machadowelton.domain.exceptions.RecursoNaoEncontradoException;
import br.com.machadowelton.domain.exceptions.ValidacaoException;
import br.com.machadowelton.services.UsuarioService;
import br.com.machadowelton.services.impl.UsuarioServiceImpl;
import br.com.machadowelton.services.repositories.users.UsuarioRepository;

@RunWith(SpringRunner.class)
public class UsuarioServiceImplTest {
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	private UsuarioService usuarioService;
	
	private Usuario usuarioSemId;
	private Usuario usuarioComId;
	private Usuario usuarioAtualizar;
	
	private final String USUARIO_1_NOME = "Usuario 1";
	private final String USUARIO_1_CPF = "12345678989";
	private final String USUARIO_1_TELEFONE = "1125361478";
	private final String USUARIO_1_ENDERECO = "Rua dos Tupiniquis";
	private final String USUARIO_1_EMAIL = "email@email.com";
	
	private final String USUARIO_2_ID = "5e317d47454259438cd2f5f1";
	private final String USUARIO_2_NOME = "Usuario 2";
	private final String USUARIO_2_CPF = "98765432121";
	private final String USUARIO_2_TELEFONE = "1136258978";
	private final String USUARIO_2_ENDERECO = "Rua das Pamonha";
	private final String USUARIO_2_EMAIL = "email2@email.com";
	
	
	private final String USUARIO_3_ID = "5e317d47454259438cd2f5f1";
	private final String USUARIO_3_NOME = "Usuario 3";
	private final String USUARIO_3_CPF = "98765132121";
	private final String USUARIO_3_TELEFONE = "1136258978";
	private final String USUARIO_3_ENDERECO = "Rua das Pamonha";
	private final String USUARIO_3_EMAIL = "email2@email.com";
	
	
	@Before
	public void setUp() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
		usuarioSemId = Usuario.builder()
								.nomeCompleto(USUARIO_1_NOME)
								.cpf(USUARIO_1_CPF)
								.telefone(USUARIO_1_TELEFONE)
								.endereco(USUARIO_1_ENDERECO)
								.email(USUARIO_1_EMAIL)
								.build();
		usuarioComId = Usuario.builder()
								.id(USUARIO_2_ID)
								.nomeCompleto(USUARIO_2_NOME)
								.cpf(USUARIO_2_CPF)
								.telefone(USUARIO_2_TELEFONE)
								.endereco(USUARIO_2_ENDERECO)
								.email(USUARIO_2_EMAIL)
								.build();
		usuarioAtualizar = Usuario.builder()
								.id(USUARIO_3_ID)
								.nomeCompleto(USUARIO_3_NOME)
								.cpf(USUARIO_3_CPF)
								.telefone(USUARIO_3_TELEFONE)
								.endereco(USUARIO_3_ENDERECO)
								.email(USUARIO_3_EMAIL)
								.build();			
	}
	
	@Test
	public void test_inserir_usuario() {
		usuarioService.inserir(usuarioSemId);
		verify(usuarioRepository).save(usuarioSemId);
	}
	
	@Test(expected = ValidacaoException.class)
	public void test_inserir_unicidade_cpf_validacao_exception() {
		when(usuarioRepository.existsByCpf(USUARIO_1_CPF)).thenReturn(Boolean.TRUE);		
		usuarioService.inserir(usuarioSemId);
		verify(usuarioRepository).save(usuarioSemId);
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_inserir_erro_interno_exception() {
		when(usuarioRepository.save(usuarioSemId)).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		usuarioService.inserir(usuarioSemId);
		verify(usuarioRepository).save(usuarioSemId);
	}
	
	@Test
	public void test_buscar_usuario_por_id() {
		when(usuarioRepository.findById(USUARIO_2_ID)).thenReturn(Optional.of(usuarioComId));
		Usuario usuario = usuarioService.buscarPorId(usuarioComId.getId());
		verify(usuarioRepository).findById(usuarioComId.getId());
		assertThat(usuario).isNotNull();
		assertThat(usuario.equals(usuarioComId)).isEqualTo(Boolean.TRUE);
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void test_buscar_usuario_por_id_usuario_nao_encontrado() {
		when(usuarioRepository.findById(USUARIO_2_ID)).thenReturn(Optional.ofNullable(null));
		usuarioService.buscarPorId(USUARIO_2_ID);
		verify(usuarioRepository).findById(USUARIO_2_ID);
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_buscar_usuario_por_id_erro_interno() {
		when(usuarioRepository.findById(USUARIO_2_ID)).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		usuarioService.buscarPorId(USUARIO_2_ID);
		verify(usuarioRepository).findById(USUARIO_2_ID);
	}
	
	@Test
	public void test_buscar_todos() {		
		when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuarioComId));
		List<Usuario> usuarios = usuarioService.listarTodos();
		verify(usuarioRepository).findAll();
		assertThat(usuarios).isNotNull();
		assertThat(usuarios.equals(Arrays.asList(usuarioComId))).isEqualTo(Boolean.TRUE);		
	}
	
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void test_buscar_todos_nenhum_usuario_encontrado() {		
		when(usuarioRepository.findAll()).thenReturn(null);
		usuarioService.listarTodos();
		verify(usuarioRepository).findAll();			
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_buscar_todos_erro_interno() {		
		when(usuarioRepository.findAll()).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		usuarioService.listarTodos();
		verify(usuarioRepository).findAll();
	}
	
	@Test
	public void test_atualizar() {				
		when(usuarioRepository.existsById(USUARIO_3_ID)).thenReturn(Boolean.TRUE);
		when(usuarioRepository.findById(USUARIO_3_ID)).thenReturn(Optional.of(usuarioAtualizar));
		when(usuarioRepository.save(usuarioAtualizar)).thenReturn(usuarioAtualizar);
		Usuario usuario = usuarioService.atualizar(USUARIO_3_ID, usuarioAtualizar);
		verify(usuarioRepository).existsById(USUARIO_3_ID);
		verify(usuarioRepository).findById(USUARIO_3_ID);		
		verify(usuarioRepository).save(usuarioAtualizar);
		assertThat(usuario.equals(usuarioAtualizar)).isEqualTo(Boolean.TRUE);
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void test_atualizar_usuario_nao_encontrado() {		
		when(usuarioRepository.existsById(USUARIO_3_ID)).thenReturn(Boolean.FALSE);
		usuarioService.atualizar(USUARIO_3_ID, usuarioAtualizar);
		verify(usuarioRepository).existsById(USUARIO_3_ID);
	}
	
	@Test(expected = ValidacaoException.class)
	public void test_atualizar_usuario_unicidade_cpf() {		
		when(usuarioRepository.existsById(USUARIO_2_ID)).thenReturn(Boolean.TRUE);
		when(usuarioRepository.findById(USUARIO_2_ID)).thenReturn(Optional.of(usuarioComId));
		when(usuarioRepository.existsByCpf(USUARIO_3_CPF)).thenReturn(Boolean.TRUE);
		usuarioService.atualizar(USUARIO_2_ID, usuarioAtualizar);		
		verify(usuarioRepository).existsById(USUARIO_2_ID);
		verify(usuarioRepository).findById(USUARIO_2_ID);
		verify(usuarioRepository).existsByCpf(usuarioAtualizar.getCpf());
		verify(usuarioRepository).save(usuarioAtualizar);
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_atualizar_usuario_erro_interno() {				
		when(usuarioRepository.existsById(USUARIO_2_ID)).thenReturn(Boolean.TRUE);
		when(usuarioRepository.findById(USUARIO_3_ID)).thenReturn(Optional.of(usuarioComId));
		when(usuarioRepository.existsByCpf(USUARIO_3_CPF)).thenReturn(Boolean.FALSE);
		when(usuarioRepository.save(usuarioAtualizar)).thenThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição"));
		usuarioService.atualizar(USUARIO_3_ID, usuarioAtualizar);
		verify(usuarioRepository).existsById(USUARIO_3_ID);
		verify(usuarioRepository).findById(USUARIO_3_ID);
		verify(usuarioRepository).existsByCpf(usuarioAtualizar.getCpf());
		verify(usuarioRepository.save(usuarioAtualizar));
	}
	
	
	@Test
	public void test_deletar_usuario() {				
		when(usuarioRepository.existsById(USUARIO_3_ID)).thenReturn(Boolean.TRUE);
		usuarioService.removerPorId(USUARIO_3_ID);
		verify(usuarioRepository).deleteById(USUARIO_3_ID);
	}
	
	@Test(expected = RecursoNaoEncontradoException.class)
	public void test_deletar_usuario_usuario_nao_encontrado() {				
		when(usuarioRepository.existsById(USUARIO_3_ID)).thenReturn(Boolean.FALSE);
		usuarioService.removerPorId(USUARIO_3_ID);
		verify(usuarioRepository).deleteById(USUARIO_3_ID);
	}
	
	@Test(expected = ErroInternoException.class)
	public void test_deletar_usuario_erro_interno() {				
		when(usuarioRepository.existsById(USUARIO_3_ID)).thenReturn(Boolean.TRUE);
		Mockito.doThrow(new ErroInternoException("Ocorreu um erro ao processar a requisição")).when(usuarioRepository).deleteById(USUARIO_3_ID);
		usuarioService.removerPorId(USUARIO_3_ID);
		verify(usuarioRepository).deleteById(USUARIO_3_ID);
	}
}
