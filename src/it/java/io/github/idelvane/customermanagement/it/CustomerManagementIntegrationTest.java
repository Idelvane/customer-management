package io.github.idelvane.customermanagement.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringRunner;

import io.github.idelvane.customermanagement.dto.CustomerDTO;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
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
				.birthDate(CustomerApiUtil.getLocalDateTimeFromString("1986-05-21T07:40:15.100".concat("Z")))
				.createdAt(CustomerApiUtil.getLocalDateTimeFromString("2021-10-12T09:16:15.100".concat("Z")))
				.updatedAt(CustomerApiUtil.getLocalDateTimeFromString("2021-10-12T09:16:16.100".concat("Z"))).build(); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "T3ST3-12356709");
        
        //Create a new HttpEntity
        final HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDTO, headers);
        
		ResponseEntity<CustomerDTO> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/customer-management/v1/customers", HttpMethod.POST, entity, new ParameterizedTypeReference<CustomerDTO>(){});
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    
//    @Test
//    @Order(2)
//    public void testFindAllBetweenCreatedAt() throws ParseException {
//    	
//    	final HttpHeaders headers = new HttpHeaders();
//        headers.set("X-api-key", "T3ST3-12356709");
//        
//        //Create a new HttpEntity
//        final HttpEntity<String> entity = new HttpEntity<>(headers);
//        
//        String startDate = LocalDate.of(2021, 10, 10).toString();
//		String endDate = LocalDate.of(2021, 10, 20).toString();
//
//		ResponseEntity<String> responseEntity = this.restTemplate
//        		.exchange("http://localhost:" + port + "/customer-management/v1/customers?startDate=" + startDate 
//        				+ "&endDate=" + endDate + "&page=" + 1 + "&size=" + 2 + "&order=ASC", HttpMethod.GET, 
//        				entity, String.class);
//    	
//        assertEquals(200, responseEntity.getStatusCodeValue());
//    }
	    
}
