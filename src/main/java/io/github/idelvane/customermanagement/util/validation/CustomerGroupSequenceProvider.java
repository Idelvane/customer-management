package io.github.idelvane.customermanagement.util.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import io.github.idelvane.customermanagement.model.Customer;
/**
 * Group Provider criado para possibilitar a validação de CPF ou CNPJ de acordo com o tipo de pessoa do cliente
 * @author idelvane
 *
 */
public class CustomerGroupSequenceProvider implements DefaultGroupSequenceProvider<Customer>{

	@Override
	public List<Class<?>> getValidationGroups(Customer customer) {
		List<Class<?>> groups = new ArrayList<>();
		groups.add(Customer.class);
		if (customer != null && customer.getPersonType() != null) {
			groups.add(customer.getPersonType().getGroup());
		}
			
		return groups;
	}

}
