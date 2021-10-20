package io.github.idelvane.customermanagement.model.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.idelvane.customermanagement.enums.RoleEnum;
import io.github.idelvane.customermanagement.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe que implementa um factory para criar {@link JwtUser}
 * 
 * @author Idelvane
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUserFactory {
	
	/**
	 * Méotod que criar um JwtUser.
	 * 
	 * @param user
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), createGrantedAuthorities(user.getRole()));
	}
	
	/**
	 * Método que dar autoridade ao JwtUser.
	 * 
	 * @param role
	 */
	private static List<GrantedAuthority> createGrantedAuthorities(RoleEnum role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

}
