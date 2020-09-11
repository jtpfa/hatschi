package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerMapper;
import de.pcmr.shop.api.model.CustomerRegistrationDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.service.RegistrationServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(RegistrationApiImpl.REGISTRATION_URI)
public class RegistrationApiImpl implements RegistrationApiI {
    private final RegistrationServiceI registrationService;

    @Autowired
    public RegistrationApiImpl(RegistrationServiceI registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void registerCustomer(@Valid CustomerRegistrationDTO customerRegistrationDTO) throws KeycloakEndpointNotFoundException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakUnknownErrorException {
        registrationService.registerCustomer(CustomerMapper.mapCustomerRegistrationDtoToCustomerEntity(customerRegistrationDTO));
    }
}
