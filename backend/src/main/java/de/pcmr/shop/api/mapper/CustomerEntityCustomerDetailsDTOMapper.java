package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.domain.CustomerEntity;

public final class CustomerEntityCustomerDetailsDTOMapper {
    private CustomerEntityCustomerDetailsDTOMapper() {
        throw new IllegalStateException();
    }

    public static CustomerDetailsDTO mapCustomerEntityToCustomerDetailsDto(CustomerEntity customerEntity) {
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
        customerDetailsDTO.setEmail(customerEntity.getEmail());
        customerDetailsDTO.setFirstName(customerEntity.getFirstName());
        customerDetailsDTO.setLastName(customerEntity.getLastName());

        return customerDetailsDTO;
    }
}
