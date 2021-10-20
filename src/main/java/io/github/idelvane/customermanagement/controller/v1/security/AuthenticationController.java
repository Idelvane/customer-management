package io.github.idelvane.customermanagement.controller.v1.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.idelvane.customermanagement.dto.response.Response;
import io.github.idelvane.customermanagement.dto.security.JwtUserDTO;
import io.github.idelvane.customermanagement.dto.security.TokenDTO;
import io.github.idelvane.customermanagement.util.ApiUtils;
import io.github.idelvane.customermanagement.util.security.JwtTokenUtil;

/**
 * Controller responsável pela autenticação de usuários para consumir a API
 * @author idelvane
 *
 */
@RestController
@RequestMapping("/customer-management/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * Método que gera um token JWT valida para autorização de acesso à API
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
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDTO>> generateTokenJwt(@RequestHeader(value=ApiUtils.API_VERSION, defaultValue = "${api.version}") 
		String apiVersion, @Valid @RequestBody JwtUserDTO dto, BindingResult result) throws AuthenticationException {
		
		Response<TokenDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
		String token = jwtTokenUtil.getToken(userDetails);
		response.setData(new TokenDTO(token));
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ApiUtils.API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
}
