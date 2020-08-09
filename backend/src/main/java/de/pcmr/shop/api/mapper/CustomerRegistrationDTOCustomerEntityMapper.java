package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.util.ValidationUtils;

public final class CustomerRegistrationDTOCustomerEntityMapper {
    private CustomerRegistrationDTOCustomerEntityMapper() {
        throw new IllegalStateException();
    }

    public static CustomerEntity mapCustomerRegistrationDtoToCustomerEntity(CustomerRegistrationDTO customerRegistrationDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerRegistrationDTO.getEmail().trim().toLowerCase());
        customerEntity.setFirstName(ValidationUtils.validateNoHtml(customerRegistrationDTO.getFirstName().trim()));
        customerEntity.setLastName(ValidationUtils.validateNoHtml(customerRegistrationDTO.getLastName().trim()));
        customerEntity.setPassword(customerRegistrationDTO.getPassword());

        return customerEntity;
    }
}
