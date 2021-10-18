package io.github.idelvane.customermanagement.test.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.idelvane.customermanagement.dto.CustomerDTO;
import io.github.idelvane.customermanagement.model.Customer;
import io.github.idelvane.customermanagement.service.CustomerService;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class CustomerControllerTest {

	
	static final Long ID = 1L;
	static final String NAME = "Antonio";
	static final String DOCUMENT = "001.001.001-11";
	static final String EMAIL = "email.de.teste@teste.com";
	static final String PHONE = "(86) 99999-0000";
	static final String BIRTH_DATE = "1986-05-21T07:40:15.100";
	static final String CREATED_AT = "2021-10-12T09:16:15.100";
	static final String UPDATED_AT = "2021-10-12T09:16:16.100";
	
	static final String URL = "/customer-management/v1/customers";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	CustomerService customerService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
	}
	
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(customerService.save(Mockito.any(Customer.class))).willReturn(getMockCustomer());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getPayloadDTO(ID, NAME, DOCUMENT, EMAIL, PHONE, CustomerApiUtil.convertStringToLocalDateTime(BIRTH_DATE.concat("Z")), 
				CustomerApiUtil.convertStringToLocalDateTime(CREATED_AT.concat("Z")), CustomerApiUtil.convertStringToLocalDateTime(UPDATED_AT.concat("Z"))))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(NAME))
		.andExpect(jsonPath("$.data.document").value(DOCUMENT))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.phone").value(PHONE))
		.andExpect(jsonPath("$.data.birthDate").value(BIRTH_DATE))
		.andExpect(jsonPath("$.data.createdAt").value(CREATED_AT))
		.andExpect(jsonPath("$.data.updatedAt").value(UPDATED_AT));
	}
	
	@Test
	@Order(2)
	public void testSaveInvalidCustomer() throws Exception {
		
		BDDMockito.given(customerService.save(Mockito.any(Customer.class))).willReturn(getMockCustomer());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getPayloadDTO(ID, NAME, null, EMAIL, PHONE, CustomerApiUtil.convertStringToLocalDateTime(BIRTH_DATE.concat("Z")), 
				CustomerApiUtil.convertStringToLocalDateTime(CREATED_AT.concat("Z")), CustomerApiUtil.convertStringToLocalDateTime(UPDATED_AT.concat("Z"))))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.headers(headers))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.details").value("O documento não pode ficar em branco"));
	}
	
	private Customer getMockCustomer() throws ParseException {
		
		Customer customer = new Customer(ID, NAME, DOCUMENT, EMAIL, PHONE, CustomerApiUtil.convertStringToLocalDateTime(BIRTH_DATE.concat("Z")), 
				CustomerApiUtil.convertStringToLocalDateTime(CREATED_AT.concat("Z")), CustomerApiUtil.convertStringToLocalDateTime(UPDATED_AT.concat("Z")));
		return customer;
	}
	
	private String getPayloadDTO(Long id, String name, String document, String email, String phone, LocalDateTime birthDate, LocalDateTime createdAt, LocalDateTime updatedAt) 
			throws JsonProcessingException {
		
		CustomerDTO dto = CustomerDTO.builder().name(name).document(document).email(email).phone(phone)
									.birthDate(birthDate).createdAt(createdAt).updatedAt(updatedAt).build();
	        
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	}
}
