package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.api.model.CustomerDetailsRoleDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface CustomerApiI {
    CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException;
    void updateCustomer(@Valid CustomerDetailsDTO customerDetailsDTO, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;
    void deleteCustomer(String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException;
    List<CustomerDetailsDTO> getAllCustomers() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;
    void updateCustomer(String email, @Valid CustomerDetailsDTO customerDetailsDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException;
    void updateCustomer(@PathVariable String email, @RequestBody @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException;
}
