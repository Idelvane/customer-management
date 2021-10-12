package io.github.idelvane.customermanagement.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import io.github.idelvane.customermanagement.dto.CustomerDTO;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerManagementIntegrationTest {

	static final Long ID = 1L;
	static final String NAME = "Antonio";
	static final String DOCUMENT = "001.001.001-11";
	static final String EMAIL = "email.de.teste@teste.com";
	static final String PHONE = "(86) 99999-0000";
	static final String BIRTH_DATE = "1986-05-21T07:40:15.100";
	static final String CREATED_AT = "2021-10-12T09:16:15.100";
	static final String UPDATED_AT = "2021-10-12T09:16:16.100";
	
	@LocalServerPort
	private int port;
		 
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    @Order(1)
    public void testCreateCustomer() throws ParseException {
    	
        CustomerDTO customerDTO = CustomerDTO.builder().name(NAME).document(DOCUMENT).email(EMAIL)
        		.phone(PHONE)
				.birthDate(CustomerApiUtil.getLocalDateTimeFromString(BIRTH_DATE.concat("Z")))
				.createdAt(CustomerApiUtil.getLocalDateTimeFromString(CREATED_AT.concat("Z")))
				.updatedAt(CustomerApiUtil.getLocalDateTimeFromString(UPDATED_AT.concat("Z"))).build(); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
        
        //Create a new HttpEntity
        final HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers);
        
		ResponseEntity<CustomerDTO> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/customer-management/v1/customers", HttpMethod.POST, entity, new ParameterizedTypeReference<CustomerDTO>(){});
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
	    
}
