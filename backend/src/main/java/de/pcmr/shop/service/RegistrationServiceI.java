package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;

import javax.validation.Valid;

public interface RegistrationServiceI {
    void registerCustomer(@Valid CustomerEntity customerEntity) throws KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException;
}
