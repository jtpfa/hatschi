package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;

import javax.validation.Valid;

/**
 * Interface of customer registration service.
 * It allows users to create a customer account.
 *
 * @author Fynn Lohse
 */

public interface RegistrationServiceI {

    /**
     * Method creates a new customer. It also creates the related keycloak user.
     *
     * @param customerEntity new customer
     * @throws KeycloakUserAlreadyExistsException if the keycloak user already exists
     * @throws KeycloakUserIsNotAuthorizedException if the keycloak admin user is not authorized
     * @throws CustomerAlreadyExistsException if the customer already exists
     * @throws KeycloakEndpointNotFoundException if the keycloak rest endpoints are not found
     * @throws KeycloakUnknownErrorException if any other keycloak error occurs
     */

    void registerCustomer(@Valid CustomerEntity customerEntity) throws KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException;
}
