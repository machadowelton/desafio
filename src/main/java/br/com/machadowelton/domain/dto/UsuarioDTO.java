package br.com.machadowelton.domain.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends Usuario {
	
	
	public UsuarioDTO(String id, 
					String nomeCompleto, 
					String cpf, String telefone, String endereco, String email, List<Produto> produtos) {
		super(id, nomeCompleto, cpf, telefone, endereco, email);
		this.produtos = produtos;
	}
	
	
	List<Produto> produtos = new ArrayList<Produto>();
	
}
