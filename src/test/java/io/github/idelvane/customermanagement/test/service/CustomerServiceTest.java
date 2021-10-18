package io.github.idelvane.customermanagement.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.idelvane.customermanagement.enums.PersonTypeEnum;
import io.github.idelvane.customermanagement.model.Customer;
import io.github.idelvane.customermanagement.repository.CustomerRepository;
import io.github.idelvane.customermanagement.service.CustomerService;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;

/**
 * Responsável pelos testes do service de Customer {@link CustomerService}
 * @author idelvane
 *
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	
	/**
	 * testa a criação de um novo cliente
	 * @throws ParseException
	 */
	@Test
	@Order(1)
	public void testSave() throws ParseException {
		
		BDDMockito.given(customerRepository.save(Mockito.any(Customer.class)))
			.willReturn(getMockCustomer());
		Customer response = customerService.save(new Customer());
		
		assertNotNull(response);
		assertEquals("00100000000", response.getDocument());
	}
	
	/**
	 * Testa a busca de clientes por documento
	 * 
	 */
	@Test
	@Order(2)
	public void testFindByDocument() {
		
		BDDMockito.given(customerRepository.findByDocument(Mockito.anyString()))
			.willReturn(Optional.of(new Customer()));
		
		Optional<Customer> response = customerService.findByDocument("00100100100");
		assertTrue(!response.isEmpty());
	}
	
	/**
	 * Retorna os clientes cadastrados em um período
	 * @throws ParseException 
	 * 
	 */
	@Test
	@Order(3)
	public void testFindAllBetweenCreatedAt() throws ParseException {
		
		List<Customer> transactions = new ArrayList<>();
		transactions.add(getMockCustomer());
		Page<Customer> page = new PageImpl<>(transactions);
		
		BDDMockito.given(customerRepository.findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(Mockito.any(LocalDateTime.class), 
				Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).willReturn(page);
		
		Page<Customer> response = customerService.findBetweenCreatedAt(LocalDateTime.now(), LocalDateTime.now(), PageRequest.of(1, 10, Sort.by("id").ascending()));
		assertNotNull(response);
	}
	
	/**
	 * Retorna um mock de customer para os testes
	 * 
	 */
	private Customer getMockCustomer() throws ParseException {
		
		Customer customer = new Customer(1L, "José", "00100000000", "jose@deteste.com", "(86) 99985-9999", PersonTypeEnum.FISICA, 
				CustomerApiUtil.convertStringToLocalDateTime("1986-05-21T07:40:15.100".concat("Z")), 
				LocalDateTime.now(), LocalDateTime.now());
		return customer;
	}
	
	/**
	 * Method to remove all Travel test data.
	 * 
	 */
	@AfterAll
	private void tearDown() {
		customerRepository.deleteAll();
	}
}
