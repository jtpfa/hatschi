package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;

import java.util.List;

public interface EmployeeApiI {
    List<CustomerDetailsDTO> getAllCustomers() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;
}
