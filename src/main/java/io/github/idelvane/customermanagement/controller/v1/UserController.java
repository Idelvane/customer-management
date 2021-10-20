/**
 * 
 */
package io.github.idelvane.customermanagement.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.idelvane.customermanagement.dto.UserDTO;
import io.github.idelvane.customermanagement.dto.response.Response;
import io.github.idelvane.customermanagement.model.User;
import io.github.idelvane.customermanagement.service.UserService;
import io.github.idelvane.customermanagement.util.ApiUtils;

/**
 * @author idelvane
 *
 */
@RestController
@RequestMapping("/customer-management/v1/users")
public class UserController {


	UserService userService;
	
	@Autowired
	public UserController(UserService service) {
		this.userService = service;
	}
	
	/**
	 * Método que cria um usuário da API
	 * 
	 * @param dto
	 * @param result
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
	 */
	@PostMapping
	public ResponseEntity<Response<UserDTO>> create(@RequestHeader(value=ApiUtils.API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @Valid @RequestBody UserDTO dto, BindingResult result){
		
		Response<UserDTO> response = new Response<>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User user = userService.save(dto.convertDTOToEntity());
		UserDTO userDTO = user.convertEntityToDTO();
		
		//Self link
		createSelfLink(user, userDTO);
		response.setData(userDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ApiUtils.API_VERSION,  apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}

	/**
	 * Método que cria o self link para Usuario
	 * 
	 * @param user
	 * @param userDTO
	 */
	private void createSelfLink(User user, UserDTO userDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(user.getId()).withSelfRel();
		userDTO.add(selfLink);
	}
}
