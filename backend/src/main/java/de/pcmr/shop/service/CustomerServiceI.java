package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.domain.CustomerRoleEnum;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;

import javax.validation.Valid;
import java.security.Principal;

public interface CustomerServiceI {
    CustomerEntity getCurrentCustomer(Principal principal) throws NoCustomerFoundException;
    void updateCurrentCustomer(@Valid CustomerEntity customerEntity, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException;
    void updateCustomer(String email, @Valid CustomerEntity customerEntity, CustomerRoleEnum roleOfCallingPrincipal) throws CustomerAlreadyExistsException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException, NotAuthorizedException;
    void updateCustomer(String email, @Valid CustomerEntity customerEntity, CustomerRoleEnum roleOfCallingPrincipal, CustomerRoleEnum targetCustomerRole) throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, NotAuthorizedException, NoCustomerFoundException;
    void deleteCustomer(String email, CustomerRoleEnum roleOfCallingPrincipal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NotAuthorizedException;
}
