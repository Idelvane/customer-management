package io.github.idelvane.customermanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;

import com.fasterxml.jackson.core.JsonParseException;

import io.github.idelvane.customermanagement.dto.response.Response;


/**
 * Essa classe é responsável por manipular as exceções e erros da API usandoo o conceito de {@link ControllerAdvice}, assim temos o direcionamento 
 * correto de exceções quando ocorrerem
 * 
 * @author Idelvane
 *
 */
@ControllerAdvice
public class CustomerExceptionHandler<T> {
	
	
	/**
	 * Metodo que trata a exceção {@link CustomerNotFoundException} e retorna um status code = 404.
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { CustomerNotFoundException.class })
    protected ResponseEntity<Response<T>> handleCustomerNotFoundException(CustomerNotFoundException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
	
	/**
	 * Metodo que trata uma HttpClientErrorException, o retono é o código de Conflict - status code = 409.
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpClientErrorException.Conflict.class })
    protected ResponseEntity<Response<T>> handleConflictException(HttpClientErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
	
	/**
	 * Método que trata HttpMessageNotReadableException ou JsonParseException e retorna Unprocessable Entity com status code = 422.
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpMessageNotReadableException.class, JsonParseException.class })
    protected ResponseEntity<Response<T>> handleMessageNotReadableException(Exception exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
	
	/**
	 * Método para tratar um HttpClientErrorException e retorna TooManyRequests error com status code = 429.
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpClientErrorException.TooManyRequests.class })
    protected ResponseEntity<Response<T>> handleTooManyRequestException(HttpClientErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }
	
	/**
	 * Método para tratar erro interno no servidor Internal Server Error com status code = 500.
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { ServerErrorException.class })
    protected ResponseEntity<Response<T>> handleAPIException(ServerErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
