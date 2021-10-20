package io.github.idelvane.customermanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.model.security.JwtUserFactory;
import io.github.idelvane.customermanagement.service.UserService;

/**
 * Classe que implementa {@link UserDetailsService} e carrega um usuário pelo nome de usuário, no caso o e-mail
 * 
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	/**
	 * @see UserDetailsService#loadUserByUsername(String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userService.findByEmail(username);

		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}

		throw new UsernameNotFoundException("Usuário não encontrado.");
	}

}
