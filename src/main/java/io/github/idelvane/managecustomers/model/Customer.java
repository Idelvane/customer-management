package io.github.idelvane.managecustomers.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@Column(name = "birth_date")
	private Date birthDate;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	/**
	 * método responsável por retornar a idade do cliente
	 * @return
	 */
	public int getAge() {
		final LocalDate dataAtual = LocalDate.now();
		var birth = LocalDateTime.ofInstant(this.birthDate.toInstant(), ZoneOffset.UTC).toLocalDate();
	    final Period periodo = Period.between(birth, dataAtual);
	    return periodo.getYears();
	}
}
