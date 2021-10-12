package io.github.idelvane.customermanagement.test.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.idelvane.customermanagement.model.Customer;
import io.github.idelvane.customermanagement.repository.CustomerRepository;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;

/**
 * Classe responsável por testar as funcionalidades de {@link CustomerRepository}
 * @author idelvane
 *
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class CustomerRepositoryTest {

	static final Long ID = 1L;
	static final String NAME = "Antonio";
	static final String DOCUMENT = "001.001.001-11";
	static final String EMAIL = "email.de.teste@teste.com";
	static final String PHONE = "(86) 99999-0000";
	static final String BIRTH_DATE = "1986-05-21T07:40:15.100";
	
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * Responsável por criar um customer
	 * 
	 * @throws ParseException 
	 */
	@BeforeAll
	private void setUp() throws ParseException {
		
		Customer customer = new Customer(null, NAME, DOCUMENT, EMAIL, PHONE, CustomerApiUtil.getLocalDateTimeFromString(BIRTH_DATE.concat("Z")), 
				LocalDateTime.now(), LocalDateTime.now());

		customerRepository.save(customer);
		
	}
	
	/**
	 * Teste do metodo save do {@link CustomerRepository}
	 * 
	 * @throws ParseException 
	 */
	@Test
	@Order(1)
	public void testSave() throws ParseException {
		
		Customer customerNew = new Customer(null, "José", "000.000.000-01", EMAIL, PHONE, CustomerApiUtil.getLocalDateTimeFromString(BIRTH_DATE.concat("Z")), 
				LocalDateTime.now(), LocalDateTime.now());
		
		var response = customerRepository.save(customerNew);
		assertNotNull(response);
	}
	
	/**
	 * Teste do método findByName de {@link CustomerRepository}
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByName(){
		
		Optional<Customer> response = customerRepository.findByName("Antonio");
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Deleta todos os customers criados
	 */
	@AfterAll
	private void tearDown() {
		customerRepository.deleteAll();
	}
}
