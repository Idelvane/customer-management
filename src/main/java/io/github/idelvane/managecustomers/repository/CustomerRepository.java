package io.github.idelvane.managecustomers.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.idelvane.managecustomers.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	/**
	 * Busca todos os clientes cadastrados em um período
	 * 
	 * @return 
	 */
	Page<Customer> findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual
		(LocalDateTime startDate, LocalDateTime endDate, Pageable pg);
	
	/**
	 * Retorna um cliente pelo nome
	 * 
	 * 
	 * @return 
	 */
	Optional<Customer> findByName(String name);
	
	/**
	 * Retorna um cliente pelo documento
	 * 
	 * 
	 * @return 
	 */
	Optional<Customer> findByDocument(String document);
	
	/**
	 * Retorna um cliente pelo email
	 * 
	 * 
	 * @return 
	 */
	Optional<Customer> findByEmail(String email);
	
	
	/**
	 * Retorna um cliente pelo nome
	 * 
	 * 
	 * @return 
	 */
	Optional<Customer> findByBirthDate(LocalDateTime birthDate);
	
}
