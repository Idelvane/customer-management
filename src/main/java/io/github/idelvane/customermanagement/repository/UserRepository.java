package io.github.idelvane.customermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.idelvane.customermanagement.model.User;

/**
 * Interface que implementa o repositório para {@link User}, possui metodo padrão do JPA CRUD e outros personalizados
 * @author idelvane
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
}
