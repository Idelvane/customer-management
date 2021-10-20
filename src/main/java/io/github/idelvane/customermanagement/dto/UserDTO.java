package io.github.idelvane.customermanagement.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.util.ApiUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO da classe {@link User}
 * @author idelvane
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends RepresentationModel<UserDTO>{

	private Long id;
	
	@NotNull(message = "Nome precisa ser informado.")
	@Length(min=3, max=255, message="O nome deve conter entre 03 e 255 caracteres.")
	private String name;
	
	@NotNull(message = "A Senha precisa ser informada.")
	@Length(min=6, message="A senha deve conter no mínimo 06 caracteres.")
	private String password;
	
	@Length(max=100, message="E-mail pode ter no máximo 100 caracteres.")
	@Email(message="E-mail inválido.")
	private String email;
	
	@NotNull(message="O role de acesso precisa ser informado.")
	@Pattern(regexp="^(ADMIN|USER)$", 
		message="Para o campo role de acesso, são permitidos somente os valores ADMIN ou USER.")
	private String role;
	

	public String getPassword() {
		return ApiUtils.getHash(this.password);
	}
	
	/**
	 * Método que converte um UserDTO para {@link User}
	 * 
	 * @param dto
	 */
	public User convertDTOToEntity() {
		return new ModelMapper().map(this, User.class);
	}
}
