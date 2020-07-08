package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerDtoCustomerEntityMapper;
import de.pcmr.shop.api.model.CustomerDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.service.RegistrationServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(RegistrationApiImpl.REGISTRATION_URI)
public class RegistrationApiImpl implements RegistrationApiI {
    public static final String REGISTRATION_URI = "/api/registration";

    private final RegistrationServiceI registrationService;

    @Autowired
    public RegistrationApiImpl(RegistrationServiceI registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void registerCustomer(@RequestBody @Valid CustomerDTO customerDTO) throws KeycloakEndpointNotFoundException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException {
        registrationService.registerCustomer(CustomerDtoCustomerEntityMapper.mapCustomerDtoToCustomerEntity(customerDTO));
    }
}
