package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.util.ValidationUtils;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for mapping DTOs from or to CustomerEntities
 *
 * @author Fynn Lohse
 */

public class CustomerMapper {
    private CustomerMapper() {
        throw new IllegalStateException();
    }

    /**
     * Method maps CustomerDetailsDTO to CustomerEntity.
     *
     * @param customerDetailsDTO CustomerDetailsDTO
     * @return CustomerEntity
     */

    public static CustomerEntity mapCustomerDetailsDTOToCustomerEntity(CustomerDetailsDTO customerDetailsDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerDetailsDTO.getEmail().trim().toLowerCase());
        customerEntity.setFirstName(ValidationUtils.validateNoHtml(customerDetailsDTO.getFirstName()).trim());
        customerEntity.setLastName(ValidationUtils.validateNoHtml(customerDetailsDTO.getLastName()).trim());

        return customerEntity;
    }

    /**
     * Method maps CustomerEntity to CustomerDetailsDTO.
     *
     * @param customerEntity CustomerEntity
     * @return CustomerDetailsDTO
     */

    public static CustomerDetailsDTO mapCustomerEntityToCustomerDetailsDto(CustomerEntity customerEntity) {
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
        customerDetailsDTO.setEmail(customerEntity.getEmail());
        customerDetailsDTO.setFirstName(customerEntity.getFirstName());
        customerDetailsDTO.setLastName(customerEntity.getLastName());

        return customerDetailsDTO;
    }

    /**
     * Method maps CustomerRegistrationDTO to CustomerEntity.
     *
     * @param customerRegistrationDTO CustomerRegistrationDTO
     * @return CustomerEntity
     */

    public static CustomerEntity mapCustomerRegistrationDtoToCustomerEntity(CustomerRegistrationDTO customerRegistrationDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerRegistrationDTO.getEmail().trim().toLowerCase());
        customerEntity.setFirstName(ValidationUtils.validateNoHtml(customerRegistrationDTO.getFirstName().trim()));
        customerEntity.setLastName(ValidationUtils.validateNoHtml(customerRegistrationDTO.getLastName().trim()));
        customerEntity.setPassword(customerRegistrationDTO.getPassword());

        return customerEntity;
    }

    /**
     * Method maps List of UserRepresentations (Keycloak) to List of CustomerDetailsDTOs.
     *
     * @param userRepresentations List of UserRepresentation
     * @return List of CustomerDetailsDTO
     */

    public static List<CustomerDetailsDTO> mapUserRepresentationListToCustomerDtoList(List<UserRepresentation> userRepresentations) {
        return userRepresentations.stream().map(CustomerMapper::mapUserRepresentationToCustomerDto).collect(Collectors.toList());
    }

    /**
     * Method maps UserRepresentation (Keycloak) to CustomerDetailsDTO.
     *
     * @param userRepresentation UserRepresentation
     * @return CustomerDetailsDTO
     */

    public static CustomerDetailsDTO mapUserRepresentationToCustomerDto(UserRepresentation userRepresentation) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userRepresentation, CustomerDetailsDTO.class);
    }
}
