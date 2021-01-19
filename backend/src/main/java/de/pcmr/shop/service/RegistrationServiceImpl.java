package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Implementation of registration service interface.
 * @see de.pcmr.shop.service.RegistrationServiceI
 * @author Fynn Lohse
 */

@Service
@Validated
public class RegistrationServiceImpl implements RegistrationServiceI {
    private final CustomerRepository customerRepository;
    private final KeycloakServiceI keycloakService;

    @Autowired
    public RegistrationServiceImpl(CustomerRepository customerRepository, KeycloakServiceI keycloakService) {
        this.customerRepository = customerRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public void registerCustomer(@Valid CustomerEntity customerEntity) throws KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException {
        keycloakService.createKeycloakUser(customerEntity);
        saveCustomerEntityIfNotExists(customerEntity);
    }

    private void saveCustomerEntityIfNotExists(CustomerEntity customerEntity) throws CustomerAlreadyExistsException {
        if (customerRepository.findByEmail(customerEntity.getEmail()).isEmpty()) {
            customerRepository.save(customerEntity);
        } else {
            throw new CustomerAlreadyExistsException();
        }
    }
}
