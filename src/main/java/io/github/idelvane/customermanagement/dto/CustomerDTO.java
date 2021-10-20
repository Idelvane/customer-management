package io.github.idelvane.customermanagement.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import io.github.idelvane.customermanagement.enums.PersonTypeEnum;
import io.github.idelvane.customermanagement.model.Customer;
import io.github.idelvane.customermanagement.util.validation.CnpjGroup;
import io.github.idelvane.customermanagement.util.validation.CpfGroup;
import io.github.idelvane.customermanagement.util.validation.CustomerGroupSequenceProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO da classe {@link Customer}
 * @author idelvane
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@GroupSequenceProvider(CustomerGroupSequenceProvider.class)
public class CustomerDTO extends RepresentationModel<CustomerDTO>{
	
	private Long id;
	
	@NotNull(message="O nome não pode ficar em branco")
	private String name;
	
	@NotNull(message="O documento não pode ficar em branco")
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	private String document;
	
	@NotNull(message="O e-mail não pode ficar em branco")
	@Email(message="E-mail inválido.")
	private String email;
	
	
	private String phone;
	
	@NotNull
	private PersonTypeEnum personType;
	
	private int age;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
	private LocalDateTime birthDate;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
	private LocalDateTime createdAt;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
	private LocalDateTime updatedAt;

	/**
	 * método responsável pela conversão do DTO para entity
	 * @return
	 */
	public Customer convertDTOToEntity() {
		return new ModelMapper().map(this, Customer.class);
	}

	
}
