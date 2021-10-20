package io.github.idelvane.customermanagement.dto.security;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para encapsular o token retornado na requisição ao controller /auth
 * @author idelvane
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

	@NotNull
	private String token;
}
