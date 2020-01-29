package br.com.machadowelton.services.repositories.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.machadowelton.domain.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
	boolean existsByCpf(String cpf);
	
}
