/**
 * 
 */
package io.github.idelvane.managecustomers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.idelvane.managecustomers.exceptions.CustomerNotFoundException;
import io.github.idelvane.managecustomers.model.Customer;

/**
 * Interface de serviço para manipulação dos objetos clientes
 * 
 * @author idelvane
 *
 */
public interface CustomerService {

	/**
	 * salva um objeto cliente 
	 * 
	 * @param customer
	 * @return
	 */
	Customer save(Customer customer);
	
	/**
	 * deleta um objeto cliente a partir do ID
	 * @param id
	 */
	void deleteById(Long id);
	
	/**
	 * busca o cliente pelo ID
	 * @param id
	 * @return
	 * @throws CustomerNotFoundException
	 */
	Customer findById(Long id) throws CustomerNotFoundException;
	
	/**
	 * busca um cliente pelo nome
	 * @param name
	 * @return
	 */
	Optional<Customer> findByName(String name);
	
	/**
	 * busca um cliente pelo documento
	 * @param name
	 * @return
	 */
	
	Optional<Customer> findByDocument(String document);
	
	/**
	 * busca um cliente pela data de nascimento
	 * @param name
	 * @return
	 */
	Optional<Customer> findByBirthDate(LocalDateTime birthDate);
	
	/**
	 * Busca todos os clientes cadastrados
	 * @param name
	 * @return
	 */
	List<Customer> findAll();
	
	/**
	 * Encontra todos os clientes a partir de um período observando a data de criação
	 * 
	 * @param startDate - data inicial da busca
	 * @param endDate - data final da busca
	 * @param pageable - manipulação do objeto pagina, podendo realizar a ordenação do mesmo
	 *  
	 * @return
	 */
	Page<Customer> findBetweenCreatedAt(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
}
