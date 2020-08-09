package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.util.ValidationUtils;

public class CustomerDetailsDTOCustomerEntityMapper {
    private CustomerDetailsDTOCustomerEntityMapper() {
        throw new IllegalStateException();
    }

    public static CustomerEntity mapCustomerDetailsDTOToCustomerEntity(CustomerDetailsDTO customerDetailsDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerDetailsDTO.getEmail().trim().toLowerCase());
        customerEntity.setFirstName(ValidationUtils.validateNoHtml(customerDetailsDTO.getFirstName()).trim());
        customerEntity.setLastName(ValidationUtils.validateNoHtml(customerDetailsDTO.getLastName()).trim());

        return customerEntity;
    }
}
