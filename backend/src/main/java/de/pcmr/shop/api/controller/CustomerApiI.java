package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.security.Principal;

public interface CustomerApiI {
    CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException;
    void updateCustomer(@Valid CustomerDetailsDTO customerDetailsDTO, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;
    void deleteCustomer(@PathVariable String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException;
}
