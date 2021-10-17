package io.github.idelvane.customermanagement.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

	
	@LocalServerPort
	private int port;
		 
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    @Order(1)
    public void testCreateCustomer1() throws ParseException {
    	
        CustomerDTO customerDTO = CustomerDTO.builder().name("Antonio").document("001.001.001-11").email("email.de.teste@teste.com")
        		.phone("(86) 99999-0000")
				.birthDate(CustomerApiUtil.convertStringToLocalDateTime("1986-05-21T07:40:15.100Z"))
				.createdAt(CustomerApiUtil.convertStringToLocalDateTime("2021-10-12T09:16:15.100Z"))
				.updatedAt(CustomerApiUtil.convertStringToLocalDateTime("2021-10-12T09:16:16.100Z")).build(); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
        
        final HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers);
        
		ResponseEntity<CustomerDTO> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/customer-management/v1/customers", HttpMethod.POST, entity, new ParameterizedTypeReference<CustomerDTO>(){});
        
		System.out.println("Teste 1");
        assertEquals(201, responseEntity.getStatusCodeValue());
    }	
    
    @Test
    @Order(2)
    public void testCreateCustomer2() throws ParseException {
    	
        CustomerDTO customerDTO = CustomerDTO.builder().name("Maria").document("001.001.021-11").email("email.da.maria@teste.com")
        		.phone("(86) 99997-0001")
				.birthDate(CustomerApiUtil.convertStringToLocalDateTime("1990-06-21T07:40:15.100Z"))
				.createdAt(CustomerApiUtil.convertStringToLocalDateTime("2021-10-10T09:16:15.100Z"))
				.updatedAt(CustomerApiUtil.convertStringToLocalDateTime("2021-10-10T09:16:16.100Z")).build(); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
        
        final HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers);
        
		ResponseEntity<CustomerDTO> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/customer-management/v1/customers", HttpMethod.POST, entity, new ParameterizedTypeReference<CustomerDTO>(){});
        
		System.out.println("Teste 2");
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    
    @Test
    @Order(3)
    public void testFindAllBetweenCreatedAt() throws ParseException {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
        
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String startDate = LocalDate.of(2021, 10, 10).toString();
		String endDate = LocalDate.of(2021, 10, 20).toString();
		
		ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port + "/customer-management/v1/customers?startDate=" + startDate 
        				+ "&endDate=" + endDate + "&page=" + 1 + "&size=" + 5, HttpMethod.GET, 
        				entity, String.class);
    	
			
		System.out.println("Teste 3");
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
	    
}
