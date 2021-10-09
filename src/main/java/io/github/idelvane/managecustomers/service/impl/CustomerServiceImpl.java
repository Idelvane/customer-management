package io.github.idelvane.managecustomers.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.idelvane.managecustomers.exceptions.CustomerNotFoundException;
import io.github.idelvane.managecustomers.model.Customer;
import io.github.idelvane.managecustomers.repository.CustomerRepository;
import io.github.idelvane.managecustomers.service.CustomerService;

public class CustomerServiceImpl implements CustomerService{

	CustomerRepository customerRepository;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository travelRepository) {
		this.customerRepository = travelRepository;
	}

	
	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Customer findById(Long id) throws CustomerNotFoundException {
		return customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Cliente  de id=" + id + " não encontrado"));
	}

	@Override
	public Optional<Customer> findByName(String name) {
		return customerRepository.findByName(name);
	}

	@Override
	public Optional<Customer> findByDocument(String document) {
		return customerRepository.findByDocument(document);
	}

	@Override
	public Optional<Customer> findByBirthDate(LocalDateTime birthDate) {
		return customerRepository.findByBirthDate(birthDate);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Page<Customer> findBetweenCreatedAt(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
		return customerRepository.findAllByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(startDate, endDate, pageable);
	}

}
