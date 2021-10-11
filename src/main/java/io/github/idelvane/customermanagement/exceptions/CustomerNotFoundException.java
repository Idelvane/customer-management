package io.github.idelvane.customermanagement.exceptions;

public class CustomerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CustomerNotFoundException(){
		super();
	}
	
	public CustomerNotFoundException(String msg){
		super(msg);
	}
	
	public CustomerNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}

}
