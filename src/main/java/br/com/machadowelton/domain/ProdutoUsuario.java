package br.com.machadowelton.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.machadowelton.domain.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document("products_users")
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoUsuario {

	
	public ProdutoUsuario(String idProduto, String idUsuario) {
		this.idProduto = idProduto;
		this.idUsuario = idUsuario;
		this.status = EStatus.EM_USO;
	}
	
	@Id
	private String id;
	
	@NotNull(message = "Id do usuario não pode ser nulo")
	@NotEmpty(message = "Id do usuario não pode ser vazio")
	private String idUsuario;
	
	@NotNull(message = "Id do produto não pode ser nulo")
	@NotEmpty(message = "Id do produto não pode ser vazio")
	private String idProduto;
		
	private EStatus status;

}
