package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerMapper;
import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.api.model.CustomerDetailsRoleDTO;
import de.pcmr.shop.domain.CustomerRoleEnum;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.service.CustomerServiceI;
import de.pcmr.shop.service.KeycloakServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Implementation of EmployeeApi Interface.
 *
 * @author Fynn Lohse
 */

@RestController
@RequestMapping("/api")
public class EmployeeApiImpl implements EmployeeApiI {
    private final KeycloakServiceI keycloakService;
    private final CustomerServiceI customerService;

    @Autowired
    public EmployeeApiImpl(KeycloakServiceI keycloakService, CustomerServiceI customerService) {
        this.keycloakService = keycloakService;
        this.customerService = customerService;
    }

    @Override
    public List<CustomerDetailsDTO> getAllEmployees() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        return CustomerMapper.mapUserRepresentationListToCustomerDtoList(keycloakService.findAllKeycloakUsersWithRole(CustomerRoleEnum.EMPLOYEE));
    }

    @Override
    public void updateEmployee(String email, @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws NotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.updateCustomer(email, CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsRoleDTO), CustomerRoleEnum.ADMIN, customerDetailsRoleDTO.getCustomerRole());
    }

    @Override
    public void deleteEmployee(String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.deleteCustomer(email, CustomerRoleEnum.ADMIN);
    }
}
