/**
 * 
 */
package io.github.idelvane.customermanagement.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.idelvane.customermanagement.enums.RoleEnum;
import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.repository.UserRepository;

/**
 * Testa a classe {@link UserRepository}
 * @author idelvane
 *
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })

public class UserRepositoryTest {

	static final String EMAIL = "email.de.teste@teste.com";

	@Autowired
	UserRepository userRepository;
	
	User user;
	
	/**
	 * Testa a criação de um novo usuário
	 * 
	 */
	@Test
	public void testSave() {
		
		User user = new User(null, "Antonio", "159159", EMAIL, RoleEnum.ADMIN);
		User response = userRepository.save(user);
		
		assertNotNull(response);
	}
	
	/**
	 * Recupera um usuário pelo e-mail
	 * 
	 */
	@Test
	public void testFindByEmail(){
		Optional<User> response = userRepository.findByEmail(EMAIL);
		
		assertTrue(response.isPresent());
		assertEquals(EMAIL, response.get().getEmail());
	}
	
	/**
	 * Deleta os registros de teste
	 * 
	 */
	@AfterAll
	private void tearDown() {
		userRepository.deleteAll();
	}
}
