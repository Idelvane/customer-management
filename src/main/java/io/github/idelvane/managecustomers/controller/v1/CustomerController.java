package io.github.idelvane.managecustomers.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.idelvane.managecustomers.service.CustomerService;


@RestController
@RequestMapping("/manage-customer/v1/customers")
public class CustomerController {

	
	CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService service) {
		this.customerService = service;
	}
	
	
}
