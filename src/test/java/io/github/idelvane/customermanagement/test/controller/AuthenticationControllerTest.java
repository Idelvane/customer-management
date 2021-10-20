package io.github.idelvane.customermanagement.test.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.idelvane.customermanagement.controller.v1.security.AuthenticationController;
import io.github.idelvane.customermanagement.dto.security.JwtUserDTO;
import io.github.idelvane.customermanagement.enums.RoleEnum;
import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.model.security.JwtUser;
import io.github.idelvane.customermanagement.model.security.JwtUserFactory;
import io.github.idelvane.customermanagement.util.ApiUtils;

/**
 * Testa o controller {@link AuthenticationController}
 * 
 * @author Idelvane
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class AuthenticationControllerTest {

	static final String URL = "/customer-management/v1/auth";
	static final String EMAIL = "admin@teste.com";
	static final String PASSWORD = "159159";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserDetailsService userDetailsService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
	}
	
	/**
	 * Testa a geração do token JWT
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGenerateToken() throws Exception {
		
		BDDMockito.given(userDetailsService.loadUserByUsername(Mockito.any(String.class)))
			.willReturn(getMockJwtUser());
		
		 mockMvc.perform(MockMvcRequestBuilders.post(URL).header("Origin","*")
			 	.content(getPayloadDTO(EMAIL, PASSWORD))
			 	.contentType(MediaType.APPLICATION_JSON)
			 	.accept(MediaType.APPLICATION_JSON)
				.headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")));
	}
	
	/**
	 * Retorna o payload de DTO
	 * 
	 * 
	 * @param token
	 * @return <code>String</code> 
	 * 
	 * @throws JsonProcessingException
	 */
	private String getPayloadDTO(String email, String password) throws JsonProcessingException {
		JwtUserDTO dto = new JwtUserDTO(email, password);
		return new ObjectMapper().writeValueAsString(dto);
	}
	
	private JwtUser getMockJwtUser() throws ParseException {
		User user = new User(null, "Admin", ApiUtils.getHash(PASSWORD), EMAIL, RoleEnum.ADMIN);
		return JwtUserFactory.create(user);
	}
	
}
