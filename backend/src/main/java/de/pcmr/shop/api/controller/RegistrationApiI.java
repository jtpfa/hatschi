package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * REST-Controller for user registrations.
 *
 * @author Fynn Lohse
 */

@RequestMapping(RegistrationApiI.REGISTRATION_URI)
public interface RegistrationApiI {
    String REGISTRATION_URI = "/api/registration";

    @PostMapping
    void registerCustomer(@RequestBody @Valid CustomerRegistrationDTO customerRegistrationDTO) throws KeycloakEndpointNotFoundException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakUnknownErrorException;
}
