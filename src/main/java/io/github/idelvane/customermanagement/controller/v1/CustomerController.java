package io.github.idelvane.customermanagement.controller.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.idelvane.customermanagement.dto.CustomerDTO;
import io.github.idelvane.customermanagement.dto.response.Response;
import io.github.idelvane.customermanagement.exceptions.CustomerNotFoundException;
import io.github.idelvane.customermanagement.model.Customer;
import io.github.idelvane.customermanagement.service.CustomerService;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/customer-management/v1/customers")
public class CustomerController {

	
	CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService service) {
		this.customerService = service;
	}
	
	/**
	 * Método que cria novos clientes
	 * 
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param dto
	 * 
	 * @return ResponseEntity com um objeto <code>Response<CustomerDTO></code e HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 */
	@PostMapping
	@ApiOperation(value = "Rota responsável pela criação de um novo cliente")
	public ResponseEntity<Response<CustomerDTO>> create(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody CustomerDTO dto, BindingResult result){
		
		Response<CustomerDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Customer customer = dto.convertDTOToEntity(); 
		Customer customerToCreate = customerService.save(customer);

		CustomerDTO dtoSaved = customerToCreate.convertEntityToDTO();
		createSelfLink(customerToCreate, dtoSaved);

		response.setData(dtoSaved);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	
	/**
	 * Método que atualiza novos clientes
	 * 
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param dto
	 * 
	 * @return ResponseEntity com um objeto <code>Response<CustomerDTO></code e HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 */
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Rota utilizada para atualizar um cliente")
	public ResponseEntity<Response<CustomerDTO>> update(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody CustomerDTO dto, BindingResult result) 
		throws CustomerNotFoundException{
		
		Response<CustomerDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Customer customer = dto.convertDTOToEntity();
		Customer customerToUpdate = customerService.save(customer);
		
		CustomerDTO itemDTO = customerToUpdate.convertEntityToDTO();
		createSelfLink(customerToUpdate, itemDTO);
		response.setData(itemDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Método que retorna um Cliente a partir do ID
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param customerId - Id do cliente buscado
	 * @param fields - campos do Customer que devem ser retornados
	 * 
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 * @throws CustomerNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Rota utilizada para encontrar um cliente a partir do seu ID")
	public ResponseEntity<Response<CustomerDTO>> findById(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @PathVariable("id") Long customerId) throws CustomerNotFoundException {
		
		Response<CustomerDTO> response = new Response<>();
		Customer customer = customerService.findById(customerId);
		
		CustomerDTO dto = customer.convertEntityToDTO();
		
		createSelfLink(customer, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	
	/**
	 * Método que deleta um Cliente a partir do ID
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param customerId - Id do cliente buscado
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 * @throws CustomerNotFoundException
	 */
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
	
	/**
	 * Método que busca todos cos cliente por um determinado nome.
	 * 
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param customerName - nome do cliente
	 * 
	 * @return ResponseEntity com um objeto <code>Response<String></code> e HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 * @throws CustomerNotFoundException
	 */
	@GetMapping(value = "/byName/{customerName}")
	@ApiOperation(value = "Rota para encontrar clientes pelo nome")
	public ResponseEntity<Response<List<CustomerDTO>>> findByName(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("customerName") String customerName) throws CustomerNotFoundException {
		
		Response<List<CustomerDTO>> response = new Response<>();
		List<Customer> customers = customerService.findByName(customerName);
		
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException("Não foram encontrados clientes com o nome: " + customerName);
		}
		
		List<CustomerDTO> customersDTO = new ArrayList<>();
		customers.stream().forEach(t -> customersDTO.add(t.convertEntityToDTO()));
		
		customersDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (CustomerNotFoundException e) {
				log.error("Não foram encontrados clientes com o nome  {}", customerName);
			}
		});
		
		response.setData(customersDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	
	/**
	 * Método que busca todos os cliente por um determinado email.
	 * 
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param customerEmail - e-mail do cliente
	 * 
	 * @return ResponseEntity com um objeto <code>Response<String></code> e HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 * @throws CustomerNotFoundException
	 */
	@GetMapping(value = "/byEmail/{customerEmail}")
	@ApiOperation(value = "Rota para encontrar clientes pelo e-mail")
	public ResponseEntity<Response<List<CustomerDTO>>> findByEmail(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("customerName") String customerEmail) throws CustomerNotFoundException {
		
		Response<List<CustomerDTO>> response = new Response<>();
		Optional<Customer> customers = customerService.findByEmail(customerEmail);
		
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException("Não foram encontrados clientes com o e-mail: " + customerEmail);
		}
		
		List<CustomerDTO> customersDTO = new ArrayList<>();
		customers.stream().forEach(t -> customersDTO.add(t.convertEntityToDTO()));
		
		customersDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (CustomerNotFoundException e) {
				log.error("Não foram encontrados clientes com o e-mail  {}", customerEmail);
			}
		});
		
		response.setData(customersDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Método que busca todos cos cliente por um determinado nome.
	 * 
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param customerDocument - e-mail do cliente
	 * 
	 * @return ResponseEntity com um objeto <code>Response<String></code> e HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 * @throws CustomerNotFoundException
	 */
	@GetMapping(value = "/byDocument/{customerDocument}")
	@ApiOperation(value = "Rota para encontrar clientes pelo documento")
	public ResponseEntity<Response<List<CustomerDTO>>> findByDocument(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("customerName") String customerDocument) throws CustomerNotFoundException {
		
		Response<List<CustomerDTO>> response = new Response<>();
		Optional<Customer> customer = customerService.findByDocument(customerDocument);
		
		if (customer.isEmpty()) {
			throw new CustomerNotFoundException("Não foi encontrado cliente com o documento: " + customerDocument);
		}
		
		List<CustomerDTO> customersDTO = new ArrayList<>();
		customer.stream().forEach(t -> customersDTO.add(t.convertEntityToDTO()));
		
		customersDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (CustomerNotFoundException e) {
				log.error("Não foram encontrado cliente com o documento  {}", customerDocument);
			}
		});
		
		response.setData(customersDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Método que busca todos os clientes cadastrados em um período
	 * 
	 * 
	 * @param apiVersion - API version atual
	 * @param apiKey - API Key para acesso das rotas
	 * @param startDate - data inicial de cadastro
	 * @param endDate - data final de cadastro
	 * 
	 * @return ResponseEntity com um objeto <code>Response<String></code> e HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created
	 * 400 - Bad Request
	 * 404 - Not Found
	 * 409 - Conflict
	 * 422 – Unprocessable Entity
	 * 429 - Too Many Requests
	 * 500, 502, 503, 504 - Server Errors
	 * 
	 * @throws CustomerNotFoundException
	 */
	@GetMapping
	@ApiOperation(value = "Rota que encontra todos os clientes de acordo com o período de cadastro")
	public ResponseEntity<Response<List<CustomerDTO>>> findAllBetweenCreatedAt(@RequestHeader(value=CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=CustomerApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") 
	    LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, 
	    @PageableDefault(page = 1, size = 10) Pageable pageable) throws CustomerNotFoundException {
		
		Response<List<CustomerDTO>> response = new Response<>();
		
		LocalDateTime startDateTime = CustomerApiUtil.convertLocalDateToLocalDateTime(startDate);
		LocalDateTime endDateTime = CustomerApiUtil.convertLocalDateToLocalDateTime(endDate);
		
		Page<Customer> customers = customerService.findBetweenCreatedAt(startDateTime, endDateTime, pageable);
		
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException("Não foram encontrados clientes cadastrados no período de " + startDate + " a " + endDate);
		}
		
		List<CustomerDTO> itemsDTO = new ArrayList<>();
		customers.stream().forEach(t -> itemsDTO.add(t.convertEntityToDTO()));
		
		itemsDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (CustomerNotFoundException e) {
				log.error("Não foram encontrados clientes cadastrados no período de {} a {}", startDate, endDate);
			}
		});
		
		response.setData(itemsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(CustomerApiUtil.HEADER_CUSTOMER_MANAGEMENT_API_VERSION, apiVersion);
		headers.add(CustomerApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Método que cria um self link para o modelo de Customer (HATEOAS)
	 * 
	 * 
	 * @param customer
	 * @param customerDTO
	 */
	private void createSelfLink(Customer customer, CustomerDTO customerDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(CustomerController.class).slash(customer.getId()).withSelfRel();
		customerDTO.add(selfLink);
	}
	
	/**
	 * Méotod que criar um self link em uma coleção de Customer
	 * 
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param customerDTO
	 * @throws CustomerNotFoundException
	 */
	private void createSelfLinkInCollections(String apiVersion, String apiKey, final CustomerDTO customerDTO) 
			throws CustomerNotFoundException {
		Link selfLink = linkTo(methodOn(CustomerController.class).findById(apiVersion, apiKey, customerDTO.getId()))
				.withSelfRel().expand();
		customerDTO.add(selfLink);
	}
	
}
