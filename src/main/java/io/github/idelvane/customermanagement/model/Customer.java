package io.github.idelvane.customermanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import io.github.idelvane.customermanagement.dto.CustomerDTO;
import io.github.idelvane.customermanagement.enums.PersonTypeEnum;
import io.github.idelvane.customermanagement.util.CustomerApiUtil;
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
@Table(name = "customer")
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Enumerated(EnumType.STRING)
	private PersonTypeEnum personType;
	
	private LocalDateTime birthDate;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	/**
	 * Converte um Customer em CustomerDTO
	 * @return
	 */
	public CustomerDTO convertEntityToDTO() {
		return new ModelMapper().map(this, CustomerDTO.class);
	}
	
	public int getAge() {
		return CustomerApiUtil.calculateAge(birthDate);
	}
}
