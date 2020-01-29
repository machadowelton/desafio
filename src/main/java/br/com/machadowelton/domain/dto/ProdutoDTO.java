package br.com.machadowelton.domain.dto;

import br.com.machadowelton.domain.Produto;
import br.com.machadowelton.domain.enums.EStatus;
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
public class ProdutoDTO extends Produto {
	
	public ProdutoDTO(String id, String name, EStatus status, String idRelacao) {
		super(id, name);
		this.status = status;
		this.idRelacao = idRelacao;
	}
	
	private EStatus status; 
	
	private String idRelacao;
}
