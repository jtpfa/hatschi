package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDTO;
import de.pcmr.shop.domain.CustomerEntity;

public class CustomerDtoCustomerEntityMapper {
    public static CustomerEntity mapCustomerDtoToCustomerEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerDTO.getEmail().trim());
        customerEntity.setFirstName(customerDTO.getFirstName().trim());
        customerEntity.setLastName(customerDTO.getLastName().trim());
        customerEntity.setPassword(customerDTO.getPassword());

        return customerEntity;
    }
}
