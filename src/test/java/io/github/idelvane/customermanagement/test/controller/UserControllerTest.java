/**
 * 
 */
package io.github.idelvane.customermanagement.test.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

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

import io.github.idelvane.customermanagement.controller.v1.UserController;
import io.github.idelvane.customermanagement.dto.UserDTO;
import io.github.idelvane.customermanagement.enums.RoleEnum;
import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.service.UserService;

/**
 * Testa o controller {@link UserController}
 * @author idelvane
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class UserControllerTest {

	static final String URL = "/customer-management/v1/users";
	static final Long ID = 1L;
	static final String NAME = "Antonio";
	static final String PASSWORD = "159159";
	static final String EMAIL = "email.de.teste@teste.com";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
	}
	
	/**
	 * Salva um usuário para utilizar a API
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).header("Origin","*").content(getPayloadDTO(ID, NAME, PASSWORD, EMAIL, RoleEnum.ADMIN))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(NAME))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.role").value(RoleEnum.ADMIN.getDescription()));
		
	}
	
	/**
	 * Cria um mock de {@link User}
	 * 
	 * @return <code>User</code> 
	 * @throws ParseException 
	 */
	private User getMockUser() throws ParseException {
		return new User(1L, NAME, PASSWORD, EMAIL, RoleEnum.ADMIN);
	}
	
	/**
	 * Retorna um mock de {@link UserDTO}
	 * 
	 * @param id
	 * @return String
	 * @throws JsonProcessingException
	 */
	private String getPayloadDTO(Long id, String name, String password, String email, RoleEnum role) throws JsonProcessingException {
		
		UserDTO dto = UserDTO.builder()
				.id(id)
				.name(name)
				.password(password)
				.email(email)
				.role(role.getDescription())
				.build();
		return new ObjectMapper().writeValueAsString(dto);
	}
}
