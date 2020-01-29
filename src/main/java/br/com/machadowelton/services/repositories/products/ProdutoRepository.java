package br.com.machadowelton.services.repositories.products;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.machadowelton.domain.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
	
	Optional<Produto> findByName(String name);
	
}
