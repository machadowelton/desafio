package br.com.machadowelton.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document("users")
@Data
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	private String id;
	
	@NotNull(message = "Nome não pode ser nulo")
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nomeCompleto;
	
	@NotNull(message = "Cpf não pode ser nulo")
	@NotEmpty(message = "Cpf não pode ser vazio")
	@Indexed(unique = true)
	private String cpf;
	
	private String telefone;
	
	
	@NotNull(message = "Endereço não pode ser nulo")
	@NotEmpty(message = "Endereço não pode ser vazio")
	private String endereco;
	
	@NotNull(message = "Email não pode ser nulo")
	@NotEmpty(message = "Email não pode ser vazio")
	@Email
	private String email;
	
}
