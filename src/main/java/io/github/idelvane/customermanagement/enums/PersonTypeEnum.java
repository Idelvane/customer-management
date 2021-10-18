package io.github.idelvane.customermanagement.enums;

import lombok.AllArgsConstructor;
import io.github.idelvane.customermanagement.util.validation.*;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonTypeEnum {

	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class),
	JURIDICA("Juridica", "CNPJ", "00.000.000/0000-00", CpfGroup.class);
	
	private String description;
	private String documentType;
	private String mask;
	private Class<?> group;
}
