package io.github.idelvane.customermanagement.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import io.github.idelvane.customermanagement.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * Classe responsável por encapsular o conceito de cliente
 * 
 * @author idelvane
 *
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "travel")
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@NotBlank
	private String name;
	
	@NotNull
	@NotBlank
	private String document;
	
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	private String phone;
	
	@Column(name = "birth_date")
	private LocalDateTime birthDate;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	/**
	 * método responsável por retornar a idade do cliente
	 * @return
	 */
	public int getAge() {
		final LocalDate now = LocalDate.now();
	    final Period period = Period.between(this.birthDate.toLocalDate(), now);
	    return period.getYears();
	}

	/**
	 * Converte um Customer em CustomerDTO
	 * @return
	 */
	public CustomerDTO convertEntityToDTO() {
		return new ModelMapper().map(this, CustomerDTO.class);
	}
}
