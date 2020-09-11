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
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerApiImpl implements CustomerApiI {
    private final CustomerServiceI customerService;
    private final KeycloakServiceI keycloakService;

    @Autowired
    public CustomerApiImpl(CustomerServiceI customerService, KeycloakServiceI keycloakService) {
        this.customerService = customerService;
        this.keycloakService = keycloakService;
    }

    @Override
    public CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException {
        return CustomerMapper.mapCustomerEntityToCustomerDetailsDto(customerService.getCurrentCustomer(principal));
    }

    @Override
    public void updateCustomer(@Valid CustomerDetailsDTO customerDetailsDTO, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        customerService.updateCurrentCustomer(CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsDTO), principal);
    }

    @Override
    public List<CustomerDetailsDTO> getAllCustomers() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        return CustomerMapper.mapUserRepresentationListToCustomerDtoList(keycloakService.findAllKeycloakUsersWithRole(CustomerRoleEnum.CUSTOMER));
    }

    @Override
    public void updateCustomer(String email, @Valid CustomerDetailsDTO customerDetailsDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException {
        customerService.updateCustomer(email, CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsDTO), CustomerRoleEnum.EMPLOYEE);
    }

    @Override
    public void updateCustomer(String email, @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException {
        customerService.updateCustomer(email, CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsRoleDTO), CustomerRoleEnum.ADMIN, customerDetailsRoleDTO.getCustomerRole());
    }

    @Override
    public void deleteCustomer(String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.deleteCustomer(email, CustomerRoleEnum.ADMIN);
    }
}
