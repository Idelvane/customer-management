package io.github.idelvane.customermanagement.service;

import java.util.Optional;

import io.github.idelvane.customermanagement.model.User;

/**
 * Interface para manipulação do objetos {@link User}
 * 
 * @author idelvane
 *
 */
public interface UserService {

	/**
	 * Salva um novo usuario 
	 * 
	 * 
	 * @param user
	 * @return User object
	 */
	User save(User user);
	
	/**
	 * Recupera um usuário pelo email
	 * 
	 * 
	 * @param email
	 * @return Optional<User> object
	 */
	Optional<User> findByEmail(String email);
}
