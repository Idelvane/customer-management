/**
 * 
 */
package io.github.idelvane.customermanagement.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.repository.UserRepository;
import io.github.idelvane.customermanagement.service.UserService;

/**
 * Implementação dos métodos de {@link UserService}
 * @author idelvane
 *
 */
@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.userRepository = repository;
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
