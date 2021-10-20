/**
 * 
 */
package io.github.idelvane.customermanagement.dto.security;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para JWT Authentication 
 * 
 * @author idelvane
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class JwtUserDTO {
	
	@NotNull(message = "Informe o e-mail")
	@NotEmpty(message = "Informe o e-mail")
	private String email;
	
	@NotNull(message = "Informe a senha")
	@NotEmpty(message = "Informe a senha")
	private String password;


}
