/**
 * 
 */
package io.github.idelvane.managecustomers.service;

import io.github.idelvane.managecustomers.exceptions.CustomerNotFoundException;
import io.github.idelvane.managecustomers.model.Customer;

/**
 * Interface de serviço para manipulação dos objetos clientes
 * 
 * @author idelvane
 *
 */
public interface CustomerService {

	Customer save(Customer customer);
	
	void deleteById(Long id);
	
	Customer findById(Long id) throws CustomerNotFoundException;
	
}
