package io.github.idelvane.customermanagement.test.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.idelvane.customermanagement.enums.RoleEnum;
import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.repository.UserRepository;
import io.github.idelvane.customermanagement.service.UserService;

/**
 * Testa a classe {@link UserService}
 * @author idelvane
 *
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class UserServiceTest {

	@Autowired
	private UserService service;

	@MockBean
	private UserRepository userRepository;
	
	static final String EMAIL = "email.de.teste@teste.com";
	
	/**
	 * Criar um usuario
	 * 
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(userRepository.save(Mockito.any(User.class)))
			.willReturn(getMockUser());
		User response = service.save(new User());
		
		assertNotNull(response);
	}
	
	/**
	 * Buusca um usário pelo email
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByEmail(){
		
		BDDMockito.given(userRepository.findByEmail(Mockito.anyString()))
			.willReturn(Optional.ofNullable(new User()));

		Optional<User> response = service.findByEmail(EMAIL);
		assertTrue(!response.isEmpty());
	}
	
	/**
	 * Criar um mock de {@link User}
	 * 
	 * @return <code>User</code> object
	 */
	private User getMockUser() {
		return new User(1L, "Antonio", "159159", EMAIL, RoleEnum.ADMIN);
	}
	
	/**
	 * Deleta usuarios de teste
	 * 
	 */
	@AfterAll
	private void tearDown() {
		userRepository.deleteAll();
	}
}
