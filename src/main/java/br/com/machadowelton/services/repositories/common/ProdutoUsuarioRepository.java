package br.com.machadowelton.services.repositories.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.machadowelton.domain.ProdutoUsuario;
import br.com.machadowelton.domain.enums.EStatus;


@Repository
public interface ProdutoUsuarioRepository extends MongoRepository<ProdutoUsuario, String> {
	
	Optional<ProdutoUsuario> findByIdUsuarioAndStatus(String idUsuario, EStatus status);
	
	List<ProdutoUsuario> findByIdUsuario(String idUsuario);
	
	List<ProdutoUsuario> findByStatusAndIdProduto(EStatus status, String idProduto);
	
	List<ProdutoUsuario> findByStatus(EStatus status);
	
}
