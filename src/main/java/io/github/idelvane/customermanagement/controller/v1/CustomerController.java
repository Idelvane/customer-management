package io.github.idelvane.customermanagement.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.idelvane.customermanagement.dto.response.Response;
import io.github.idelvane.customermanagement.exceptions.CustomerNotFoundException;
import io.github.idelvane.customermanagement.model.Customer;
import io.github.idelvane.customermanagement.service.CustomerService;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/customer-management/v1/customers")
public class CustomerController {

	
	CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService service) {
		this.customerService = service;
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Rota responsável por deletar um cliente via API")
	public ResponseEntity<Response<String>> delete(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("id") Long customerId) throws CustomerNotFoundException {
		
		Response<String> response = new Response<>();
		Customer customer = customerService.findById(customerId);
		
		customerService.deleteById(customer.getId());
		response.setData("Customer com id:" + customer.getId() + ", deletado com sucesso");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
	}
	
}
