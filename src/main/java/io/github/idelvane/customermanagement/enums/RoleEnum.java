package io.github.idelvane.customermanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum que representa os papeis de um usuário no sistema 
 * @author idelvane
 *
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

	ADMIN("ADMIN"),
	USER("USER");
	
	private String description;
}
