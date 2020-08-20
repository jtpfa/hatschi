package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.util.ValidationUtils;

public class CustomerMapper {
    private CustomerMapper() {
        throw new IllegalStateException();
    }

    public static CustomerEntity mapCustomerDetailsDTOToCustomerEntity(CustomerDetailsDTO customerDetailsDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerDetailsDTO.getEmail().trim().toLowerCase());
        customerEntity.setFirstName(ValidationUtils.validateNoHtml(customerDetailsDTO.getFirstName()).trim());
        customerEntity.setLastName(ValidationUtils.validateNoHtml(customerDetailsDTO.getLastName()).trim());

        return customerEntity;
    }

    public static CustomerDetailsDTO mapCustomerEntityToCustomerDetailsDto(CustomerEntity customerEntity) {
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
        customerDetailsDTO.setEmail(customerEntity.getEmail());
        customerDetailsDTO.setFirstName(customerEntity.getFirstName());
        customerDetailsDTO.setLastName(customerEntity.getLastName());

        return customerDetailsDTO;
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
